package com.example.coffee.product.controller.dto;

public record UpdateProductRequest(
        String name,
        Integer price,
        String image
) {
}
