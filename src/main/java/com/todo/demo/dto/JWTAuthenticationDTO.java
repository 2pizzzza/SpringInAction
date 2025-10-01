package com.todo.demo.dto;

import lombok.Data;

@Data
public class JWTAuthenticationDTO {
    private String token;
    private String refreshToken;
}
