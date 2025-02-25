package com.example.coffee.product.controller;

import com.example.coffee.product.controller.dto.CreateProductRequest;
import com.example.coffee.product.controller.dto.ProductResponse;
import com.example.coffee.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Product API", description = "상품 관련 API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(CreateProductRequest createProductRequest) {
        productService.create(createProductRequest);
    }

    @Operation(summary = "상품 목록 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct() {
        return productService.getAll();
    }
}
