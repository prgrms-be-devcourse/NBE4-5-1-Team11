package com.example.coffee.product.service;

import com.example.coffee.product.controller.dto.CreateProductRequest;
import com.example.coffee.product.controller.dto.ProductResponse;
import com.example.coffee.product.controller.dto.UpdateProductRequest;
import com.example.coffee.product.domain.Product;
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

    @Transactional
    public void update(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        if (request.name() != null) product.updateName(request.name());
        if (request.price() != null) product.updatePrice(request.price());
        if (request.image() != null) product.updateImage(request.image());

        productRepository.save(product);
    }
}
