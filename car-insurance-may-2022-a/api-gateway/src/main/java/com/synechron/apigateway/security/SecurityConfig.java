package com.synechron.apigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .pathMatchers("/api/auth/login", "/api/auth/password/**", "/api/auth/token/refresh", "/api/auth/log-out")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/api/brands/**")
                .hasAnyRole("ADMIN", "AGENT")
                .pathMatchers("/api/brands/**")
                .hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/api/countries/**")
                .hasAnyRole("ADMIN", "AGENT")
                .pathMatchers("/api/countries/**")
                .hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/api/drivers/**")
                .hasAnyRole("ADMIN", "AGENT")
                .pathMatchers("/api/drivers/**")
                .hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/api/insurance-items/**")
                .hasAnyRole("ADMIN", "AGENT")
                .pathMatchers("/api/insurance-items/**")
                .hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/api/insurance-plans/**")
                .hasAnyRole("ADMIN", "AGENT")
                .pathMatchers("/api/insurance-plans/**")
                .hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/api/payment-modes/**")
                .hasAnyRole("ADMIN", "AGENT")
                .pathMatchers("/api/proposals/**")
                .hasAnyRole("ADMIN", "AGENT")
                .pathMatchers(HttpMethod.GET, "/api/subscribers/**")
                .hasAnyRole("ADMIN", "AGENT")
                .pathMatchers("/api/auth/users/**")
                .hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .addFilterBefore(new AuthFilter(webClient()), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}
