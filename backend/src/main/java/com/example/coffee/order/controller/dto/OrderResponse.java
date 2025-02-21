package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.Order;

public record OrderResponse(
        Long userId,
        String address,
        String code,
        Integer totalPrice
) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getUserId(), order.getAddress(), order.getCode(), order.getTotalPrice());
    }
}
