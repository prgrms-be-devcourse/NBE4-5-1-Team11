package com.example.coffee.config;

import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.order.domain.repository.OrderProductRepository;
import com.example.coffee.order.domain.repository.OrderRepository;
import com.example.coffee.product.domain.Product;
import com.example.coffee.product.domain.repository.ProductRepository;
import com.example.coffee.user.domain.User;
import com.example.coffee.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @Override
    public void run(String... args) {
        /*if (orderRepository.count() > 0) {
            System.out.println("✅ 이미 초기 데이터가 존재하므로 추가하지 않습니다.");
            return;
        }

        // 제품 데이터 삽입
        Product americano = productRepository.save(new Product(1L, "Americano", 5000));
        Product latte = productRepository.save(new Product(2L, "Latte", 5500));
        Product cappuccino = productRepository.save(new Product(3L, "Cappuccino", 6000));
        Product greenTea = productRepository.save(new Product(4L, "GreenTea", 4500));

        // 유저 데이터 삽입
        User user1 = userRepository.save(new User("han000@naver.com"));
        User user2 = userRepository.save(new User("tjdwl3245@gmail.com"));
        User user3 = userRepository.save(new User("aaaa1111@naver.com"));
        User user4 = userRepository.save(new User("bbbb222@gmail.com"));

        // 주문 데이터 삽입 (첫 번째 주문)
        Order order1 = orderRepository.save(
                Order.builder()
                        .user(user2)
                        .address("울산시 중구")
                        .code("55555")
                        .totalPrice(16500)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        // 주문 상품 데이터 삽입 (첫 번째 주문)
        orderProductRepository.saveAll(List.of(
                OrderProduct.builder()
                        .order(order1)
                        .product(cappuccino)
                        .price(6000)
                        .quantity(2)
                        .build(),
                OrderProduct.builder()
                        .order(order1)
                        .product(greenTea)
                        .price(4500)
                        .quantity(1)
                        .build()
        ));

        // 주문 데이터 삽입 (두 번째 주문)
        Order order2 = orderRepository.save(
                Order.builder()
                        .user(user3)
                        .address("서울시 강남구")
                        .code("12345")
                        .totalPrice(11000)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        // 주문 상품 데이터 삽입 (두 번째 주문)
        orderProductRepository.saveAll(List.of(
                OrderProduct.builder()
                        .order(order2)
                        .product(americano)
                        .price(5000)
                        .quantity(1)
                        .build(),
                OrderProduct.builder()
                        .order(order2)
                        .product(latte)
                        .price(5500)
                        .quantity(1)
                        .build()
        ));

        System.out.println("✅ 초기 데이터 삽입 완료!");*/
    }
}
