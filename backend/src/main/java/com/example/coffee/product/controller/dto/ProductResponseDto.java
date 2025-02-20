package com.example.coffee.product.controller.dto;

import com.example.coffee.product.domain.Product;

public record ProductResponseDto(
        String name,

        String image,

        int price
) {
    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(product.getName(), product.getImage(), product.getPrice());
    }
}
