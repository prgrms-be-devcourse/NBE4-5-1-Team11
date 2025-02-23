package com.example.coffee.product.service;

import com.example.coffee.product.controller.dto.CreateProductRequest;
import com.example.coffee.product.controller.dto.ProductResponse;
import com.example.coffee.product.domain.Product;
import com.example.coffee.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private static final String FILE_PATH = "uploads/products";

    @Transactional
    public void createProduct(CreateProductRequest dto, MultipartFile image) {

        String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
        System.out.println(fileName);

        try {
            Path targetLocation = Paths.get(FILE_PATH).toAbsolutePath().resolve(fileName);
            Files.createDirectories(targetLocation.getParent());
            image.transferTo(targetLocation.toFile());
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 저장에 실패했습니다.");
        }

        productRepository.save(dto.toEntity(fileName));
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(product -> {
                    try {
                        Path imagePath = Paths.get(FILE_PATH).toAbsolutePath().resolve(product.getImage());
                        byte[] imageBytes = Files.readAllBytes(imagePath);
                        return ProductResponse.of(product, imageBytes);
                    } catch (IOException e) {
                        throw new IllegalArgumentException("이미지 파일을 읽는 데 실패했습니다.");
                    }
                })
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        try {
            Path imagePath = Paths.get(FILE_PATH).toAbsolutePath().resolve(product.getImage());
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 파일을 삭제하는 데 실패했습니다.");
        }

        productRepository.delete(product);
    }
}
