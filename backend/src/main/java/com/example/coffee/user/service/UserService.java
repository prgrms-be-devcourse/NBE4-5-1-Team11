package com.example.coffee.user.service;

import com.example.coffee.order.controller.dto.OrderProductResponse;
import com.example.coffee.order.controller.dto.OrderResponse;
import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.order.domain.repository.OrderProductRepository;
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
    private final OrderProductRepository orderProductRepository;

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
        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 해당 유저의 주문 목록 조회
        List<Order> orders = orderRepository.findByUser(user);

        // 주문 목록을 OrderResponse로 변환
        return orders.stream()
                .map(order -> {
                    List<OrderProduct> orderProducts = orderProductRepository.findByOrder(order); // 주문한 제품 조회
                    List<OrderProductResponse> productResponses = orderProducts.stream()
                            .map(OrderProductResponse::from)
                            .toList();
                    return OrderResponse.from(order, productResponses);
                })
                .toList();
    }
}