package com.synechron.authservice.security.jwt;

import com.synechron.authservice.exception.AuthenticationException;
import com.synechron.authservice.model.RefreshToken;
import com.synechron.authservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@EnableConfigurationProperties(JwtConfig.class)
public class JwtUtils {
    private final JwtConfig jwtConfig;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, boolean isAccess) {
        String secret = isAccess ? jwtConfig.getAccessTokenSecret() : jwtConfig.getRefreshTokenSecret();
        final Claims claims = extractAllClaims(token, secret);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        return createAccessToken(claims, user);
    }

    public RefreshToken generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        claims.put("jti", uuid.toString());
        String refreshToken = createRefreshToken(claims, user);

        return RefreshToken.builder()
                .id(uuid)
                .refreshToken(refreshToken)
                .user(user)
                .build();
    }

    public void validateToken(String token, boolean isAccess) throws AuthenticationException {
        String secret = isAccess ? jwtConfig.getAccessTokenSecret() : jwtConfig.getRefreshTokenSecret();
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthenticationException("Expired or invalid token");
        }

    }

    public String getUsernameFromToken(String token, boolean isAccess) {
        return extractClaim(token, Claims::getSubject, isAccess);
    }

    public String getJtiFromRefreshToken(String refreshToken) {
        Claims claims = extractAllClaims(refreshToken, jwtConfig.getRefreshTokenSecret());
        return claims.get("jti", String.class);
    }

    private Claims extractAllClaims(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String createAccessToken(Map<String, Object> claims, User user) {
        return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * jwtConfig.getAccessTokenDuration()))
                .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getAccessTokenSecret()).compact();
    }

    private String createRefreshToken(Map<String, Object> claims, User user) {

        return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * jwtConfig.getRefreshTokenDuration()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getRefreshTokenSecret()).compact();
    }
}
