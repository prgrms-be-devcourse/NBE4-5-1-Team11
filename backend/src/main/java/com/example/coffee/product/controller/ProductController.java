package com.example.coffee.product.controller;

import com.example.coffee.product.controller.dto.CreateProductRequest;
import com.example.coffee.product.controller.dto.ProductResponse;
import com.example.coffee.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product API", description = "상품 관련 API")
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    
    @Operation(summary = "상품 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "상품 생성 성공")
    public void createProduct(@RequestBody CreateProductRequest dto) {
        productService.createProduct(dto);
    }

    @Operation(summary = "상품 목록 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    public List<ProductResponse> getAllProduct() {
        return productService.getAll();
    }

    @Operation(summary = "상품 단건 조회")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "상품 단건 조회 성공")
    public void deleteProduct(@PathVariable(name = "productId") Long productId) {
        productService.delete(productId);
    }
}
