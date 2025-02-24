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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private static final LocalDateTime DELIVERY_TIME = LocalDateTime.now().withHour(14).withMinute(0).withSecond(0).withNano(0);

    // 주문 결제 동시에 유저 저장, 주문 저장
    @Transactional
    public void create(CreateOrderRequest orderRequest) {
        // 유저 조회 혹은 생성 후 저장
        User user = userRepository.findByEmail(orderRequest.email())
                .orElseGet(() -> userRepository.save(new User(orderRequest.email())));

        // 주문 조회 혹은 생성 후 저장
        LocalDateTime now = LocalDateTime.now();
        Order order = findByUserOrCreate(user, orderRequest, now);

        // 주문 상품 업데이트 혹은 생성 후 저장
        updateOrderProduct(orderRequest, order);
    }

    // 주문 전체 조회
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    System.out.println(orderProductRepository.findAllByOrder(order));
                    List<OrderProductResponse> productResponses = orderProductRepository.findAllByOrder(order).stream()
                            .map(OrderProductResponse::from)
                            .toList();
                    return OrderResponse.from(order, productResponses);
                })
                .toList();
    }

    // 유저별 주문 전체 조회
    public List<OrderResponse> findAllByUser(User user) {
        System.out.println("여기까지 못 옴?");
        return orderRepository.findAllByUser(user, Sort.by(Sort.Order.desc("createdAt"))).stream()
                .map(order -> {
                    List<OrderProductResponse> productResponses = orderProductRepository.findAllByOrder(order).stream()
                            .map(OrderProductResponse::from)
                            .toList();
                    return OrderResponse.from(order, productResponses);
                })
                .toList();
    }

    // 주문 id로 주문 단건 조회
    @Transactional
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        List<OrderProductResponse> productResponses = orderProductRepository.findAllByOrder(order).stream()
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

    private Order findByUserOrCreate(User user, CreateOrderRequest orderRequest, LocalDateTime now) {
        List<Order> orderList = orderRepository.findAllByUser(user, Sort.by(Sort.Order.desc("createdAt")));

        // 첫 주문이면 생성
        if (orderList.isEmpty()) return createOrder(user, orderRequest, now);


        // 마지막 주문이 14시 전인데 orderRequest의 주문 시간은 14시 후라면 생성
        if (orderList.get(0).getCreatedAt().isBefore(DELIVERY_TIME) && now.isAfter(DELIVERY_TIME)) {
            return createOrder(user, orderRequest, now);
        }

        // 마지막 주문이 14시 전이고 orderRequest의 주문 시간도 14시 전이면 추가
        // 마지막 주문이 14시 후이고 orderRequest의 주문 시간도 14시 후라면 추가
        return orderList.get(0);
    }

    private Order createOrder(User user, CreateOrderRequest orderRequest, LocalDateTime now) {
        return orderRepository.save(orderRequest.toEntity(user, now));
    }

    private void updateOrderProduct(CreateOrderRequest orderRequest, Order order) {
        orderRequest.products().forEach(
                productRequest -> {
                    Product product = productRepository.findById(productRequest.id())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productRequest.id()));

                    OrderProduct orderProduct = orderProductRepository.findByOrderAndProduct(order, product);

                    if (orderProduct == null) {
                        orderProduct = productRequest.toEntity(order, product);
                    } else {
                        orderProduct.addQuantity(productRequest.quantity());
                    }

                    orderProductRepository.save(orderProduct);
                });
    }

//    // 주문 전체 삭제
//    @Transactional
//    public void deleteAll() {
//        orderProductRepository.deleteAll();
//        orderRepository.deleteAll();
//    }
}
