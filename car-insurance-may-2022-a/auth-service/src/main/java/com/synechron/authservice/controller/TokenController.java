package com.synechron.authservice.controller;

import com.synechron.authservice.dto.RefreshTokenResponse;
import com.synechron.authservice.exception.AuthenticationException;
import com.synechron.authservice.model.User;
import com.synechron.authservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {
    private final TokenService service;

    @PostMapping("/validate/{token}")
    public ResponseEntity<User> validateToken(@PathVariable String token) throws AuthenticationException {
        return new ResponseEntity<>(service.validateAccessToken(token), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@CookieValue(name = "refresh_token") String refreshToken) throws AuthenticationException {
        return new ResponseEntity<>(service.refreshToken(refreshToken), HttpStatus.OK);
    }
}
