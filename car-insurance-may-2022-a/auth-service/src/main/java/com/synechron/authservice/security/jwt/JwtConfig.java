package com.synechron.authservice.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("jwt")
@Getter
public final class JwtConfig {
    private final String accessTokenSecret;
    private final String refreshTokenSecret;
    private final int accessTokenDuration;
    private final int refreshTokenDuration;
}
