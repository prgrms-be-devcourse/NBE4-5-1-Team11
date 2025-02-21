package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.product.domain.Product;

public record CreateOrderProductRequest(
    Integer quantity,
    Order order,
    Product product
) {

    public OrderProduct toEntity() {
        return OrderProduct.builder()
                .quantity(quantity)
                .order(order)
                .product(product)
                .build();
    }
}
