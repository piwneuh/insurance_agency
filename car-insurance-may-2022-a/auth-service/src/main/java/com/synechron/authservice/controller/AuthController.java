package com.synechron.authservice.controller;

import com.synechron.authservice.dto.LoginRequest;
import com.synechron.authservice.dto.LoginResponse;
import com.synechron.authservice.dto.PasswordDto;
import com.synechron.authservice.dto.UserDto;
import com.synechron.authservice.exception.AuthenticationException;
import com.synechron.authservice.exception.NotFoundException;
import com.synechron.authservice.mapper.UserMapper;
import com.synechron.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService service;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginResponse loginResponse = service.login(request);
        Cookie refreshTokenCookie = new Cookie("refresh_token", loginResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        response.addCookie(refreshTokenCookie);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @DeleteMapping("/log-out")
    public ResponseEntity<Void> logout(@CookieValue(name = "refresh_token") String refreshToken, HttpServletResponse response) {
        service.logout(refreshToken);
        Cookie revokedRefreshTokenCookie = new Cookie("refresh_token", null);
        revokedRefreshTokenCookie.setHttpOnly(true);
        revokedRefreshTokenCookie.setMaxAge(0);
        response.addCookie(revokedRefreshTokenCookie);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/password/{username}")
    public ResponseEntity<Boolean> requestPasswordChange(@PathVariable String username) {
        return new ResponseEntity<>(service.requestPasswordChange(username), HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<UserDto> changePassword(@Valid @RequestBody PasswordDto passwordDto) throws AuthenticationException, NotFoundException {
        return new ResponseEntity<>(userMapper.domainToDto(service.changePassword(passwordDto)), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() throws NotFoundException {
        return new ResponseEntity<>(userMapper.domainToDto(service.getCurrentUser()), HttpStatus.OK);
    }
}
