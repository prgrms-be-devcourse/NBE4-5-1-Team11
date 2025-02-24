package com.example.coffee.order.service;

import com.example.coffee.order.controller.dto.CreateOrderRequest;
import com.example.coffee.order.controller.dto.OrderProductResponse;
import com.example.coffee.order.controller.dto.OrderResponse;
import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
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
    public OrderResponse create(CreateOrderRequest orderRequest) {
        // 유저 확인
        User user = userRepository.findByEmail(orderRequest.email())
                .orElseGet(() -> userRepository.save(new User(orderRequest.email())));

        // 주문 저장
        Order order = orderRepository.save(orderRequest.toEntity(user));

        // 주문 상품 저장
        List<OrderProductResponse> productResponses = orderRequest.products().stream().map(productRequest -> {
            Product product = productRepository.findById(productRequest.id())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productRequest.id()));

            OrderProduct orderProduct = productRequest.toEntity(order, product);
            orderProductRepository.save(orderProduct);

            return OrderProductResponse.from(orderProduct);
        }).toList();

        return OrderResponse.from(order, productResponses);
    }

    // 유저 id로 주문 전체 조회
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    List<OrderProductResponse> productResponses = orderProductRepository.findByOrder(order).stream()
                            .map(OrderProductResponse::from)
                            .toList();
                    return OrderResponse.from(order, productResponses);
                })
                .toList();
    }

    // 유저 이메일로 주문 조회
    @Transactional
    public List<OrderResponse> findAllByEmail(String email) {
        // 유저 이메일 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 유저가 존재하지 않습니다: " + email));

        // 해당 유저의 주문 목록 조회
        List<Order> orders = orderRepository.findByUser(user);

        // 주문 리스트 DTO 변환 후 반환
        return orders.stream().map(order -> {
            List<OrderProductResponse> productResponses = orderProductRepository.findByOrder(order).stream()
                    .map(OrderProductResponse::from)
                    .toList();
            return OrderResponse.from(order, productResponses);
        }).toList();
    }

    // 주문 id로 주문 단건 조회
    @Transactional
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        List<OrderProductResponse> productResponses = orderProductRepository.findByOrder(order).stream()
                .map(OrderProductResponse::from)
                .toList();

        return OrderResponse.from(order, productResponses);
    }

    // 주문 id로 주문 단건 삭제
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
