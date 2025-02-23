package com.example.coffee.product.controller.dto;

import com.example.coffee.product.domain.Product;

public record ProductResponse(
        Long id,
        String name,
        String image,
        int price
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getImage(),
                product.getPrice()
        );
    }
}
