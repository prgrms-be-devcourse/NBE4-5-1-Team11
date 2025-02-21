package com.example.coffee.user.controller.dto;

import com.example.coffee.user.domain.User;
import lombok.Builder;

@Builder
public record CreateUserRequest(
//        String name,
//        String password
          String email
){
    public User toEntity() {
        return User.builder()
                .email(email)
                // .name(name)
                // .password(password)
                .build();
    }
}
