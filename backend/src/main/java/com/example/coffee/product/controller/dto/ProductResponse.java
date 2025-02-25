package com.example.coffee.product.controller.dto;

import com.example.coffee.product.domain.Product;

public record ProductResponse(
        Long id,
        String name,
        int price,
        String image
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImage()
        );
    }
}
