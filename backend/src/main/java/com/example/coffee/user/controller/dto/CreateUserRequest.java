package com.example.coffee.user.controller.dto;

import com.example.coffee.user.domain.Authority;
import com.example.coffee.user.domain.User;
import lombok.Builder;

@Builder
public record CreateUserRequest(
          String email,
          String password
){
    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(email)
                .password(encodedPassword) // encodedPassword!!
                .authority(Authority.ROLE_USER) // 기본은 일반사용자
                .build();
    }
}
