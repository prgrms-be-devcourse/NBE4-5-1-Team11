package com.example.coffee.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id; // 주문 id

    @Column
    private String name; // 상품 이름

    @Column
    private Integer price; // 상품 가격

    @Column
    private String image; // 상품 이미지 url

}
