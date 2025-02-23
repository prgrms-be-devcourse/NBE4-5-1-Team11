package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.product.domain.Product;

public record OrderProductResponse(
        Long id,
        Order order,
        Product product,
        Integer price,
        Integer quantity
) {

    public static OrderProductResponse from(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getId(),
                orderProduct.getOrder(),
                orderProduct.getProduct(),
                orderProduct.getPrice(),
                orderProduct.getQuantity()
        );
    }
}
