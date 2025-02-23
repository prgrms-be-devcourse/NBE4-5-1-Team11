package com.example.coffee.product.controller;

import com.example.coffee.product.controller.dto.CreateProductRequest;
import com.example.coffee.product.controller.dto.ProductResponse;
import com.example.coffee.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestPart(value = "product") CreateProductRequest dto,
                              @RequestPart(value = "image") MultipartFile image) {
        productService.createProduct(dto, image);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct() {
        return productService.getAll();
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(name = "productId") Long productId) {
        productService.delete(productId);
    }
}
