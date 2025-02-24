package com.example.coffee.user.controller;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "회원 관련 API")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @Operation(summary = "회원 가입")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "회원 가입 성공")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.saveUser(request);
    }

    @Operation(summary = "회원 단건 조회")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "회원 단건 조회 성공")
    public CreateUserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "회원 목록 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공")
    public List<CreateUserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CreateUserResponse updateUser(@PathVariable Long id, @RequestBody CreateUserRequest request){
//        return userService.updateUser(id, request);
//    }

    @Operation(summary = "회원 단건 삭제")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "회원 단건 삭제 성공")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}