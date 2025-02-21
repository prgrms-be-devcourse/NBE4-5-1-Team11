package com.example.coffee.order.service;

import com.example.coffee.order.controller.dto.CreateOrderRequest;
import com.example.coffee.order.controller.dto.OrderResponse;
import com.example.coffee.order.controller.dto.ProductRequest;
import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.order.domain.repository.OrderProductRepository;
import com.example.coffee.order.domain.repository.OrderRepository;
import com.example.coffee.product.domain.Product;
import com.example.coffee.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        for (ProductRequest productRequest : request.products()) {
            Product product = productRepository.getById(productRequest.id());
            orderProductRepository.save(
                    OrderProduct.builder()
                            .order(order)
                            .product(product)
                            .quantity(productRequest.quantity())
                            .build()
            );
        }
    }

    // 주문 전체 조회
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }

    // 주문 단건 삭제
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    // 주문 전체 삭제
    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
