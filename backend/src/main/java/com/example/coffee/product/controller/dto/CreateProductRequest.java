package com.example.coffee.product.controller.dto;

import com.example.coffee.product.domain.Product;

public record CreateProductRequest(
        String name,
        int price
) {
    public Product toEntity(String fileName) {
        return Product.builder()
                .name(name)
                .image(fileName)
                .price(price)
                .build();
    }
}
