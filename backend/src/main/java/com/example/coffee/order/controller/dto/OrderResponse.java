package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.Order;
import lombok.Builder;

@Builder
public record OrderResponse(
        Long id,
        String email,
        String address,
        String code,
        Integer totalPrice
) {

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .email(order.getUser().getEmail())
                .address(order.getAddress())
                .code(order.getCode())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
