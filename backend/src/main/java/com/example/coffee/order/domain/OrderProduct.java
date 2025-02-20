package com.example.coffee.order.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id; // 주문상품 id

    @Column
    private Integer quantity; // 주문 수량

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order; // 주문 객체 연결

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Product product;
}
