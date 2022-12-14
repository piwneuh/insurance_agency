package com.synechron.authservice.mapper;

import com.synechron.authservice.dto.UserDto;
import com.synechron.authservice.model.User;
import com.synechron.authservice.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto domainToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role((UserRole) user.getAuthorities().stream().findFirst().orElse(null))
                .build();
    }

    public User dtoToDomain(UserDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .userRole(dto.getRole())
                .build();
    }
}
