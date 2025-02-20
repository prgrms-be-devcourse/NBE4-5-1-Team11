package com.example.coffee.order.service;

import com.example.coffee.order.controller.dto.CreateOrderRequest;
import com.example.coffee.order.controller.dto.ProductRequest;
import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.order.domain.repository.OrderProductRepository;
import com.example.coffee.order.domain.repository.OrderRepository;
import com.example.coffee.product.domain.Product;
import com.example.coffee.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    // 상품 주문
    public void create(CreateOrderRequest request) {

        // 유저 이메일로 유저 정보 조회

        Order order = orderRepository.save(request.toEntity());

        for (ProductRequest requestProduct : request.products()) {
            Product product = productRepository.getById(requestProduct.id());
            orderProductRepository.save(
                    OrderProduct.builder()
                            .order(order)
                            .product(product)
                            .quantity(requestProduct.quantity())
                            .build()
            );
        }
    }
}
