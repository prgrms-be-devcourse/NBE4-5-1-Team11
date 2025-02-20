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
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        User createdUser = userService.saveUser(CreateUserRequest.toEntity(request));
        return CreateUserResponse.from(createdUser);
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public CreateUserResponse getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email).get();
        return CreateUserResponse.from(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CreateUserResponse> getAllUsers(){
        List<User> users = userService.getAllUsers();
        List<CreateUserResponse> response = users.stream()
                .map(CreateUserResponse::from)
                .toList();
        return response;
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public CreateUserResponse updateUser(@PathVariable String email, @RequestBody CreateUserRequest request){
        Long userId = userService.getUserByEmail(email).get().getId();
        User updatedUser = userService.updateUser(userId, CreateUserRequest.toEntity(request));
        return CreateUserResponse.from(updatedUser);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String email){
        Long userId = userService.getUserByEmail(email).get().getId();
        userService.deleteUser(userId);
    }

}
