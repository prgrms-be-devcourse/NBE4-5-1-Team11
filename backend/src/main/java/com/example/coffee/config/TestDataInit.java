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
        if (orderRepository.count() > 0) {
            System.out.println("✅ 이미 초기 데이터 존재");
            return;
        }

        // 제품 데이터 삽입
        Product americano = productRepository.save(Product.builder().name("Americano").price(5000).build());
        Product latte = productRepository.save(Product.builder().name("Latte").price(5500).build());
        Product cappuccino = productRepository.save(Product.builder().name("Cappuccino").price(6000).build());
        Product greenTea = productRepository.save(Product.builder().name("GreenTea").price(4500).build());

        // 유저 데이터 삽입
        User user1 = userRepository.save(new User("han000@naver.com"));
        User user2 = userRepository.save(new User("tjdwl3245@gmail.com"));
        User user3 = userRepository.save(new User("aaaa1111@naver.com"));
        User user4 = userRepository.save(new User("bbbb222@gmail.com"));

        Order order1 = orderRepository.save(
                Order.builder()
                        .user(user2)
                        .address("울산시 중구")
                        .code("55555")
                        .totalPrice(16500)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        orderProductRepository.saveAll(List.of(
                OrderProduct.builder()
                        .order(order1)
                        .product(cappuccino)
                        .quantity(2)
                        .build(),
                OrderProduct.builder()
                        .order(order1)
                        .product(greenTea)
                        .quantity(1)
                        .build()
        ));

        Order order2 = orderRepository.save(
                Order.builder()
                        .user(user3)
                        .address("서울시 강남구")
                        .code("12345")
                        .totalPrice(11000)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        orderProductRepository.saveAll(List.of(
                OrderProduct.builder()
                        .order(order2)
                        .product(americano)
                        .quantity(1)
                        .build(),
                OrderProduct.builder()
                        .order(order2)
                        .product(latte)
                        .quantity(1)
                        .build()
        ));

        Order order3 = orderRepository.save(
                Order.builder()
                        .user(user1)
                        .address("경기도 오산시")
                        .code("18110")
                        .totalPrice(27000)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        orderProductRepository.saveAll(List.of(
                OrderProduct.builder()
                        .order(order3)
                        .product(cappuccino)
                        .quantity(3)
                        .build(),
                OrderProduct.builder()
                        .order(order1)
                        .product(greenTea)
                        .quantity(2)
                        .build()
        ));

        Order order4 = orderRepository.save(
                Order.builder()
                        .user(user1)
                        .address("경기도 오산시")
                        .code("202020")
                        .totalPrice(10000)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        orderProductRepository.saveAll(List.of(
                OrderProduct.builder()
                        .order(order4)
                        .product(americano)
                        .quantity(2)
                        .build()
        ));

        System.out.println("✅ 초기 데이터 삽입 완료");
    }
}
