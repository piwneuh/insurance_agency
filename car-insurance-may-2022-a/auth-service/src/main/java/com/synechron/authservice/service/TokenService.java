package com.synechron.authservice.service;

import com.synechron.authservice.dto.RefreshTokenResponse;
import com.synechron.authservice.exception.AuthenticationException;
import com.synechron.authservice.model.RefreshToken;
import com.synechron.authservice.model.User;
import com.synechron.authservice.repository.RefreshTokenRepository;
import com.synechron.authservice.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public String generateAccessToken(User user) {
        return jwtUtils.generateAccessToken(user);
    }

    public String generateRefreshToken(User user) {
        RefreshToken refreshToken = jwtUtils.generateRefreshToken(user);
        String refreshTokenString = refreshToken.getRefreshToken();
        refreshToken.setRefreshToken(passwordEncoder.encode(refreshTokenString));
        refreshTokenRepository.save(refreshToken);

        return refreshTokenString;
    }

    public User validateAccessToken(String accessToken) throws AuthenticationException {
        jwtUtils.validateToken(accessToken, true);

        return userService.loadUserByUsername(jwtUtils.getUsernameFromToken(accessToken, true));
    }

    public RefreshTokenResponse refreshToken(String refreshToken) throws AuthenticationException {
        jwtUtils.validateToken(refreshToken, false);
        UUID jti = UUID.fromString(jwtUtils.getJtiFromRefreshToken(refreshToken));
        RefreshToken token = refreshTokenRepository.findById(jti)
                .orElseThrow(() -> new AuthenticationException("Invalid refresh token"));

        if (!passwordEncoder.matches(refreshToken, token.getRefreshToken())) {
            throw new AuthenticationException("Invalid refresh token");
        }
        String username = jwtUtils.getUsernameFromToken(refreshToken, false);
        User user = userService.loadUserByUsername(username);

        return new RefreshTokenResponse(generateAccessToken(user));
    }

    public void revokeRefreshToken(String refreshToken) {
        try {
            jwtUtils.validateToken(refreshToken, false);
            UUID jti = UUID.fromString(jwtUtils.getJtiFromRefreshToken(refreshToken));
            RefreshToken token = refreshTokenRepository.findById(jti).orElse(null);
            if (token != null && passwordEncoder.matches(refreshToken, token.getRefreshToken())) {
                refreshTokenRepository.deleteById(jti);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }
}
