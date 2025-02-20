package com.example.coffee.user.controller.dto;

import com.example.coffee.user.domain.User;
import lombok.Builder;

@Builder
public record CreateUserRequest(
        String name,
        String email,
        String password
){
    public static User toEntity(CreateUserRequest createUserRequest) {
        return User.builder()
                .name(createUserRequest.name)
                .email(createUserRequest.email)
                .password(createUserRequest.password)
                .build();
    }
}
