package com.example.coffee.product.controller.dto;

import com.example.coffee.product.domain.Product;

public record CreateProductRequest(
        String name,
        int price,
        String image
) {
    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .image(image)
                .build();
    }
}
