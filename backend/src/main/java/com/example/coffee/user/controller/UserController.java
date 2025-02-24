package com.example.coffee.user.controller;

import com.example.coffee.order.controller.dto.OrderResponse;
import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
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
        return userService.saveUser(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreateUserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CreateUserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CreateUserResponse updateUser(@PathVariable Long id, @RequestBody CreateUserRequest request){
//        return userService.updateUser(id, request);
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

   // 해당 유저의 주문 정보 모두 조회
    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getUserOrders(@PathVariable Long id){
        return userService.getUserOrders(id);
    }
}