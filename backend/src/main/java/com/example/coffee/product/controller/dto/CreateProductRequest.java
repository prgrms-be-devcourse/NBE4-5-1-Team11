package com.example.coffee.product.controller.dto;

import com.example.coffee.product.domain.Product;

public record CreateProductRequest(
        String name,
        String image,
        int price
) {
    public Product toEntity() {
        return Product.builder()
                .name(name)
                .image(image)
                .price(price)
                .build();
    }
}
