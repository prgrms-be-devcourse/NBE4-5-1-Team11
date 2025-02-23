package com.example.coffee.user.controller.dto;

import com.example.coffee.user.domain.User;
import lombok.Builder;

@Builder
public record CreateUserRequest(
        String email
){
    public User toEntity() {
        return User.builder()
                .email(email)
                .build();
    }
}