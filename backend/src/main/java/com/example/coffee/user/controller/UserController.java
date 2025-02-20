package com.example.coffee.user.controller;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.domain.User;
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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        return CreateUserResponse.from(userService.saveUser(CreateUserRequest.toEntity(request)));
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public CreateUserResponse getUserByEmail(@PathVariable String email) {
        return CreateUserResponse.from(userService.getUserByEmail(email).get());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CreateUserResponse> getAllUsers(){
        return userService.getAllUsers().stream()
                .map(CreateUserResponse::from)
                .toList(); // 이렇게 하면 적절한가요?
    }

    @PutMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public CreateUserResponse updateUser(@PathVariable String email, @RequestBody CreateUserRequest request){
        return CreateUserResponse.from(userService.updateUser(userService.getUserByEmail(email).get().getId(), CreateUserRequest.toEntity(request)));
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String email){
        userService.deleteUser(userService.getUserByEmail(email).get().getId());
    }
}