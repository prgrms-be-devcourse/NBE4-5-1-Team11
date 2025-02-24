package com.example.coffee.user.controller.dto;

public record LoginRequest(
        String email,
        String password
) {
}
