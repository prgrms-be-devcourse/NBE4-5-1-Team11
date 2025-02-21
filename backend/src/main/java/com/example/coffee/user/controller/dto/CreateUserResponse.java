package com.example.coffee.user.controller.dto;

import com.example.coffee.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record CreateUserResponse(
        Long id,
        String email
//        String name,
//        String password
){
    public static CreateUserResponse from(User user){
        return new CreateUserResponse(user.getId(), user.getEmail());
        // , user.getName(), user.getPassword()
    }
}
