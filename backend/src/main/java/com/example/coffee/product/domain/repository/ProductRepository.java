package com.example.coffee.product.domain.repository;

import com.example.coffee.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
