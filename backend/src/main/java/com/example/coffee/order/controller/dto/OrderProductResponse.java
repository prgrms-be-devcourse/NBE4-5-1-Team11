package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.product.controller.dto.ProductResponse;

public record OrderProductResponse(
        Long id,
        Long orderId,
        ProductResponse product,
        Integer price,
        Integer quantity
) {

    public static OrderProductResponse from(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getId(),
                orderProduct.getOrder().getId(),
                ProductResponse.from(orderProduct.getProduct()),
                orderProduct.getPrice(),
                orderProduct.getQuantity()
        );
    }
}
