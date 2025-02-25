package com.example.coffee.product.service;

import com.example.coffee.product.controller.dto.CreateProductRequest;
import com.example.coffee.product.controller.dto.ProductResponse;
import com.example.coffee.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void create(CreateProductRequest request) {
        productRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(ProductResponse::from)
                .toList();
    }
}
