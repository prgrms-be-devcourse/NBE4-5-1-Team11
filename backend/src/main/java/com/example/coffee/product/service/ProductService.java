package com.example.coffee.product.service;

import com.example.coffee.product.controller.dto.ProductRequestDto;
import com.example.coffee.product.controller.dto.ProductResponseDto;
import com.example.coffee.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void createProduct(ProductRequestDto dto) {
        productRepository.save(dto.toEntity());
    }

    public List<ProductResponseDto> getAll() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::from)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
