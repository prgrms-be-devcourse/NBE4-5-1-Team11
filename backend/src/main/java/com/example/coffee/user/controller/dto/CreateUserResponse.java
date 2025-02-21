package com.example.coffee.user.controller.dto;

import com.example.coffee.user.domain.User;

public record CreateUserResponse(
        Long id,
        String email
){
    public static CreateUserResponse from(User user){
        return new CreateUserResponse(user.getId(), user.getEmail());
    }
}