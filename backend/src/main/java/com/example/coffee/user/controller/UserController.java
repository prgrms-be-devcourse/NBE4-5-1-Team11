package com.example.coffee.user.controller;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.controller.dto.LoginRequest;
import com.example.coffee.user.controller.dto.TokenResponse;
import com.example.coffee.user.controller.dto.RefreshTokenRequest;
import com.example.coffee.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ✅ 회원가입
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse register(@RequestBody CreateUserRequest request) {
        return userService.saveUser(request);
    }

    // ✅ 로그인
    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    // ✅ 토큰 재발행 (로그인 유지)
    @PostMapping("/refresh")
    public TokenResponse refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        return userService.refreshAccessToken(request.refreshToken());
    }

    // ✅ id로 유저 조회
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreateUserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // ✅ 유저 전체 조회
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<CreateUserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    // ✅ 유저 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}