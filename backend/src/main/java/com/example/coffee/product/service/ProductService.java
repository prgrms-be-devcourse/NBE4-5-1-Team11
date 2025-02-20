package com.example.coffee.product.service;

import com.example.coffee.product.controller.dto.ProductRequestDto;
import com.example.coffee.product.domain.Product;
import com.example.coffee.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequestDto dto) {
        Product product = Product.builder()
                .name(dto.name())
                .image(dto.image())
                .price(dto.price())
                .build();

        productRepository.save(product);
    }
}
