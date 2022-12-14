package com.synechron.apigateway.security;

import com.synechron.apigateway.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthFilter implements WebFilter {
    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
            return chain.filter(exchange);
        String authorizationHeader = exchange.getRequest().getHeaders().get(org.springframework.http.HttpHeaders.AUTHORIZATION).get(0);
        String[] parts = authorizationHeader.split(" ");

        if (parts.length != 2 || !parts[0].equals("Bearer"))
            return chain.filter(exchange);

        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/auth/token/validate/" + parts[1])
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value() == 401, response -> Mono.error(new AuthenticationException("Method not allowed. Please check the URL.")))
                .bodyToMono(User.class)
                .flatMap(user -> {
                    Authentication authentication = new PreAuthenticatedAuthenticationToken(user, null, Collections.singletonList(user.getUserRole()));
                    ServerHttpRequest request = exchange.getRequest().mutate()
                            .header("X-Authenticated-User", String.valueOf(user.getId()))
                            .build();

                    return chain.filter(exchange.mutate().request(request).build())
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                });
    }
}
