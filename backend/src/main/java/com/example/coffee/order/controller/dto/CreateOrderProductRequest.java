package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.product.domain.Product;
import lombok.Builder;

@Builder
public record CreateOrderProductRequest(
    Order order,
    Product product,
    Integer price,
    Integer quantity
) {

    public OrderProduct toEntity() {
        return OrderProduct.builder()
                .order(order)
                .product(product)
                .price(price)
                .quantity(quantity)
                .build();
    }
}
