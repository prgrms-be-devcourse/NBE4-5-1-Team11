package com.example.coffee.order.controller.dto;

import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.product.controller.dto.ProductResponse;

public record OrderProductResponse(
        ProductResponse product,
        Integer quantity
) {

    public static OrderProductResponse from(OrderProduct orderProduct) {
        return new OrderProductResponse(
                ProductResponse.from(orderProduct.getProduct()),
                orderProduct.getQuantity()
        );
    }
}
