package com.example.coffee.user.controller;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.domain.User;
import com.example.coffee.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        User createdUser = userService.saveUser(CreateUserRequest.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateUserResponse.from(createdUser));
    }

    @GetMapping("/{email}")
    public ResponseEntity<CreateUserResponse> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email).get();
        return ResponseEntity.ok(CreateUserResponse.from(user));
    }

    @GetMapping
    public ResponseEntity<List<CreateUserResponse>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        List<CreateUserResponse> response = users.stream()
                .map(CreateUserResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{email}")
    public ResponseEntity<CreateUserResponse> updateUser(@PathVariable String email, @RequestBody CreateUserRequest request){
        Long userId = userService.getUserByEmail(email).get().getId();
        User updatedUser = userService.updateUser(userId, CreateUserRequest.toEntity(request));
        return ResponseEntity.ok(CreateUserResponse.from(updatedUser));
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String email){
        Long userId = userService.getUserByEmail(email).get().getId();
        userService.deleteUser(userId);
    }

}
