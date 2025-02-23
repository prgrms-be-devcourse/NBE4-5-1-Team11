package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.product.domain.Product;

public record ProductRequest(
        Long id,
        String name,
        Integer price,
        Integer quantity
) {
    public OrderProduct toEntity(Order order, Product product) {
        return OrderProduct.builder()
                .price(price)
                .quantity(quantity)
                .order(order)
                .product(product)
                .build();
    }
}
