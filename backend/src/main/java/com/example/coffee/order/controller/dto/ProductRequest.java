package com.example.coffee.order.controller.dto;

public record ProductRequest(
        Long id,
        String name,
        Integer quantity
) { }
