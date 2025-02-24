package com.example.coffee.user.service;

import com.example.coffee.order.controller.dto.OrderResponse;
import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.repository.OrderRepository;
import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.domain.User;
import com.example.coffee.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest userDto){
        return CreateUserResponse.from(userRepository.save(userDto.toEntity()));
    }

    public List<CreateUserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(CreateUserResponse::from)
                .toList();
    }

    public CreateUserResponse getUserById(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return CreateUserResponse.from(foundUser);
    }

    public CreateUserResponse getUserByEmail(String email) {
        User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return CreateUserResponse.from(foundUser);
    }

//    @Transactional
//    public CreateUserResponse updateUser(Long id, CreateUserRequest userDto) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        user.update(userDto);
//
//        return CreateUserResponse.from(userRepository.save(user)); // 변경 사항을 저장
//    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        // 사용자 존재 여부 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 해당 사용자의 주문 목록 조회
        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(OrderResponse::from)  // Order -> OrderResponse 변환
                .toList();
    }
}