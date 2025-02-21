package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.product.domain.Product;

public record OrderProductResponse(
        Integer quantity,
        Order order,
        Product product
) {

    public static OrderProductResponse from(OrderProduct orderProduct) {
        return new OrderProductResponse(orderProduct.getQuantity(), orderProduct.getOrder(), orderProduct.getProduct());
    }
}
