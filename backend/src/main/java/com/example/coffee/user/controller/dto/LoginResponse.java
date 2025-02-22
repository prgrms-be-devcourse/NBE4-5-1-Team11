package com.example.coffee.user.controller.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
