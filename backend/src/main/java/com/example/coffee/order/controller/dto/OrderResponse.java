package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.Status;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponse(
        Long id,
        String email,
        String address,
        String code,
        Integer totalPrice,
        LocalDateTime createdAt,
        Status status,
        List<OrderProductResponse> products
) {

    public static OrderResponse from(Order order, List<OrderProductResponse> productResponses) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getEmail(),
                order.getAddress(),
                order.getCode(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getStatus(),
                productResponses
        );
    }
}
