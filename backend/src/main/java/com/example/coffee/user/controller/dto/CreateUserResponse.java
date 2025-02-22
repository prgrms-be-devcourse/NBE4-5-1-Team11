package com.example.coffee.user.controller.dto;

import com.example.coffee.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record CreateUserResponse(
        Long id,
        String email
        // 비밀번호는 반환 안 함
){
    public static CreateUserResponse from(User user){
        return new CreateUserResponse(user.getId(), user.getEmail());
    }
}
