package com.example.coffee.product.domain.repository;

import com.example.coffee.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product getById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 상품입니다."));
    }
}
