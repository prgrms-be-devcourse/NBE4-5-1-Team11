package com.example.coffee.user.controller.dto;

import com.example.coffee.user.domain.Authority;
import com.example.coffee.user.domain.User;

public record CreateUserResponse(
        Long id,
        String email,
        Authority authority
        // 비밀번호는 반환 안 함
){
    public static CreateUserResponse from(User user){
        return new CreateUserResponse(user.getId(), user.getEmail(), user.getAuthority());
    }
}
