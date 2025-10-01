package com.todo.demo.controller;

import com.todo.demo.dto.JWTAuthenticationDTO;
import com.todo.demo.dto.RefreshTokenDTO;
import com.todo.demo.dto.UserCredentialsDTO;
import com.todo.demo.service.Impl.UserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserImpl userImpl;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthenticationDTO> login(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        try {
            JWTAuthenticationDTO jwtAuthenticationDTO = userImpl.login(userCredentialsDTO);
            return ResponseEntity.ok(jwtAuthenticationDTO);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication Failed " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public JWTAuthenticationDTO refresh(@RequestBody RefreshTokenDTO refreshTokenDTO) throws Exception {
        return userImpl.refreshToken(refreshTokenDTO);
    }


}
