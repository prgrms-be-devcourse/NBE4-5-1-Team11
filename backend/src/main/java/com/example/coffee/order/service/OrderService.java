package com.example.coffee.order.service;

import com.example.coffee.order.controller.dto.CreateOrderRequest;
import com.example.coffee.order.controller.dto.OrderResponse;
import com.example.coffee.order.controller.dto.ProductRequest;
import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.repository.OrderProductRepository;
import com.example.coffee.order.domain.repository.OrderRepository;
import com.example.coffee.product.domain.Product;
import com.example.coffee.product.domain.repository.ProductRepository;
import com.example.coffee.user.domain.User;
import com.example.coffee.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 주문 결제 동시에 유저 저장, 주문 저장
    @Transactional
    public void create(CreateOrderRequest orderRequest) {
        User user = userRepository.findByEmail(orderRequest.email())
                .orElseGet(() -> userRepository.save(new User(orderRequest.email())));
        Order order = orderRepository.save(orderRequest.toEntity(user));

        for (ProductRequest productRequest : orderRequest.products()) {
            Product product = productRepository.findById(productRequest.id())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productRequest.id()));
            orderProductRepository.save(productRequest.toEntity(order, product));
        }
    }

    // 주문 전체 조회
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }

    // 주문 단건 삭제
    @Transactional
    public void delete(Long orderId) {
        orderProductRepository.deleteByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }

//    // 주문 전체 삭제
//    @Transactional
//    public void deleteAll() {
//        orderProductRepository.deleteAll();
//        orderRepository.deleteAll();
//    }
}
