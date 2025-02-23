package com.example.coffee.product.controller.dto;

import com.example.coffee.product.domain.Product;

public record ProductResponse(
        Long id,
        String name,
        byte[] image,
        int price
) {
    public static ProductResponse of(Product product, byte[] imageBytes) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                imageBytes,
                product.getPrice()
        );
    }
}
