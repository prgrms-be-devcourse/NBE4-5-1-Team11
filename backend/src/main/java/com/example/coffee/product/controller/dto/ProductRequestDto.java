package com.example.coffee.product.controller.dto;

public record ProductRequestDto(
        String name,

        String image,

        int price
) {
}
