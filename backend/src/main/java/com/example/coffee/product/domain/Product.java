package com.example.coffee.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 id

    @Column(length = 64)
    private String name; // 상품 이름

    private int price; // 상품 가격

    private String image; // 상품 이미지
}
