package com.example.coffee.user.controller.dto;

import com.example.coffee.user.domain.Token;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {

    public static TokenResponse from(Token token) {
        return new TokenResponse(token.accessToken(), token.refreshToken());
    }
}
