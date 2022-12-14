package com.synechron.authservice.service;

import com.synechron.authservice.dto.LoginRequest;
import com.synechron.authservice.dto.LoginResponse;
import com.synechron.authservice.dto.PasswordDto;
import com.synechron.authservice.exception.AuthenticationException;
import com.synechron.authservice.exception.NotFoundException;
import com.synechron.authservice.mapper.UserMapper;
import com.synechron.authservice.model.User;
import com.synechron.authservice.model.UserActivationToken;
import com.synechron.authservice.repository.UserActivationTokenRepository;
import com.synechron.authservice.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final UserActivationTokenRepository userActivationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) throws com.synechron.authservice.exception.AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (org.springframework.security.core.AuthenticationException exception) {
            throw new AuthenticationException("Invalid username or password");
        }

        User user = userService.loadUserByUsername(request.getUsername());
        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken, userMapper.domainToDto(user));
    }

    public void logout(String refreshToken) {
        tokenService.revokeRefreshToken(refreshToken);
    }

    public boolean requestPasswordChange(String username) {
        User user = userService.loadUserByUsername(username);

        UserActivationToken token = UserActivationToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        userActivationTokenRepository.save(token);
        emailService.sendMail(user.getEmail(), "Password recovery", "http://localhost:4200/change-password?token=" + token.getToken());

        return true;
    }

    public User changePassword(PasswordDto passwordDto) throws NotFoundException, AuthenticationException {
        UserActivationToken userActivationToken = userActivationTokenRepository.findFirstByTokenOrderByExpiresAtDesc(passwordDto.getToken())
                .orElseThrow(() -> new NotFoundException("Token not found"));

        if(userActivationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new AuthenticationException("Token expired");

        User userToUpdate = userActivationToken.getUser();
        userToUpdate.setPassword(passwordEncoder.encode(passwordDto.getPassword()));

        return userService.save(userToUpdate);
    }

    public User getCurrentUser() throws NotFoundException {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.findById(userId);
    }
}
