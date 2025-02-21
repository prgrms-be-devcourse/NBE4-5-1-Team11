package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.Order;
import com.example.coffee.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderRequest(
        String email,
        String address,
        String code,
        Integer totalPrice,
        List<ProductRequest> products
) {

    public Order toEntity() {
        return Order.builder()
                .user(user)
                .address(address)
                .code(code)
                .totalPrice(totalPrice)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
