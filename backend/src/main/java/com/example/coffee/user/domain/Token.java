package com.example.coffee.user.domain;

public record Token(
        String accessToken,
        String refreshToken
) {
}
