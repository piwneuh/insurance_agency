package com.synechron.authservice.service;

import com.synechron.authservice.exception.NotFoundException;
import com.synechron.authservice.exception.NotUniqueException;
import com.synechron.authservice.model.User;
import com.synechron.authservice.model.UserActivationToken;
import com.synechron.authservice.model.UserRole;
import com.synechron.authservice.repository.UserActivationTokenRepository;
import com.synechron.authservice.repository.UserRepository;
import com.synechron.authservice.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository repository;
    private final UserRoleRepository userRoleRepository;
    private final UserActivationTokenRepository userActivationTokenRepository;
    private final EmailService emailService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username %s not found", username)));
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User register(User user) throws NotUniqueException {
        if(existsByUsername(user.getUsername())) {
            throw new NotUniqueException(String.format("User with username %s already exists", user.getUsername()));
        }

        user = repository.save(user);

        UserActivationToken token = UserActivationToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        userActivationTokenRepository.save(token);

        emailService.sendMail(user.getEmail(), "Activation", "http://localhost:4200/change-password?token=" + token.getToken());

        return user;
    }

    public User update(Long id, User user) throws NotUniqueException, NotFoundException {
        User userById = findById(id);
        User userByUsername;
        try {
            userByUsername = loadUserByUsername(user.getUsername());
            if(userByUsername.getId().compareTo(id) != 0) {
                throw new NotUniqueException(String.format("User with username %s already exists", user.getUsername()));
            }
            user.setId(id);
            return repository.save(user);

        } catch (UsernameNotFoundException exception) {
            user.setId(id);
            return repository.save(user);
        }
    }

    public boolean delete(Long id) throws NotFoundException {
        User user = findById(id);
        userActivationTokenRepository.deleteAllByUserId(id);
        repository.delete(user);

        return true;
    }

    public List<UserRole> findAllRoles() {
        return userRoleRepository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }

    public User findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", id)));
    }

    private boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
}
