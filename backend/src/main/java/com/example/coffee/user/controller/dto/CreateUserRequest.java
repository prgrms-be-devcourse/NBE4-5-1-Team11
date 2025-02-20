package com.example.coffee.user.controller.dto;

import com.example.coffee.user.domain.User;
import lombok.Builder;

@Builder
public record CreateUserRequest(
        String name,
        String email,
        String password
){
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
