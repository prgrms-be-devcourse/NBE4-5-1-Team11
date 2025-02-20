package com.example.coffee.product.controller;

import com.example.coffee.product.controller.dto.ProductRequestDto;
import com.example.coffee.product.controller.dto.ProductResponseDto;
import com.example.coffee.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<Void> createProduct(ProductRequestDto dto) {
        productService.createProduct(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDto>> getAllProduct() {
        return ResponseEntity.ok(productService.getAll());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }
}
