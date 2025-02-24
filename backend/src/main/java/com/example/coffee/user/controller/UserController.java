package com.example.coffee.user.controller;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.controller.dto.LoginRequest;
import com.example.coffee.user.controller.dto.TokenResponse;
import com.example.coffee.user.controller.dto.RefreshTokenRequest;
import com.example.coffee.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "회원 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 가입")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse register(@RequestBody CreateUserRequest request) {
        return userService.saveUser(request);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @Operation(summary = "토큰 재발행 (로그인 유지)")
    @PostMapping("/refresh")
    public TokenResponse refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        return userService.refreshAccessToken(request.refreshToken());
    }

    @Operation(summary = "회원 단건 조회")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreateUserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "회원 목록 조회")
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<CreateUserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}