package com.synechron.apigateway.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements GrantedAuthority {
    public UserRole(String role) {
        name = role;
    }
    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }}
