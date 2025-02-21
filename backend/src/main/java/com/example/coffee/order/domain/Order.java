package com.example.coffee.order.domain;

import com.example.coffee.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_tbl")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id; // 주문 id
    
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 유저

    @Column()
    private LocalDateTime createdAt; // 주문 시간

    @Column
    private Integer totalPrice; // 총 가격

    @Column
    private String address; // 주소

    @Column
    private  String code; // 우편 번호

}
