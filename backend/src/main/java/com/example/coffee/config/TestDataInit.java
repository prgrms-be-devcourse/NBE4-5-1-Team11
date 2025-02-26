package com.example.coffee.config;

import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.order.domain.Status;
import com.example.coffee.order.domain.repository.OrderProductRepository;
import com.example.coffee.order.domain.repository.OrderRepository;
import com.example.coffee.product.domain.Product;
import com.example.coffee.product.domain.repository.ProductRepository;
import com.example.coffee.user.domain.Authority;
import com.example.coffee.user.domain.User;
import com.example.coffee.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (orderRepository.count() > 0 || userRepository.count() > 0) {
            System.out.println("✅ 초기 데이터 존재");
            return;
        }

        // 관리자 계정 생성
        User admin = userRepository.save(
                User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin1234"))
                        .authority(Authority.ROLE_ADMIN)
                        .build()
        );

        // 일반 유저 계정 생성
        User user1 = userRepository.save(
                User.builder()
                        .email("aaaa0000@naver.com")
                        .password(passwordEncoder.encode("user0000"))
                        .authority(Authority.ROLE_USER).build()
        );
        User user2 = userRepository.save(
                User.builder()
                        .email("bbbb1111@gmail.com")
                        .password(passwordEncoder.encode("user1111"))
                        .authority(Authority.ROLE_USER).build()
        );
        User user3 = userRepository.save(
                User.builder().email("cccc2222@naver.com")
                        .password(passwordEncoder.encode("user2222"))
                        .authority(Authority.ROLE_USER).build()
        );
        User user4 = userRepository.save(
                User.builder().email("dddd3333@gmail.com")
                        .password(passwordEncoder.encode("user3333"))
                        .authority(Authority.ROLE_USER).build()
        );

        // 제품 데이터 삽입
        Product product1 = productRepository.save(Product.builder().name("Brazil Serra Do Caparaó").price(5000).image("https://i.imgur.com/O05RZ6Y.png").build());
        Product product2 = productRepository.save(Product.builder().name("Columbia Nariñó").price(5500).image("https://i.imgur.com/PxLJVR8.png").build());
        Product product3 = productRepository.save(Product.builder().name("Columbia Quindío").image("https://i.imgur.com/NvPAcBm.png").price(5500).build());
        Product product4 = productRepository.save(Product.builder().name("Ethiopia Sidamo").image("https://i.imgur.com/BeAPnQR.png").price(6000).build());

        // 시간 기준 설정
        LocalDateTime now = LocalDateTime.now();

        // 주문 데이터 삽입
        List<Order> orders = List.of(
                createOrder(user1, "서울시 강남구", "12345", 11000, now.minusWeeks(1), product1, 1, product2, 1),
                createOrder(user2, "울산시 중구", "55555", 16500, now.minusDays(2), product3, 2, product4, 1),
                createOrder(user3, "경기도 오산시", "18110", 27000, now.minusDays(1).with(LocalTime.of(10, 0)), product3, 3, product4, 2), // 전날 오전 10시
                createOrder(user4, "부산시 해운대구", "303030", 8000, now.minusDays(1).with(LocalTime.of(16, 0)), product1, 1, product2, 1), // 전날 오후 4시
                createOrder(user1, "대전시 서구", "505050", 15000, now.minusHours(10), product4, 2, product2, 1), // 당일 오전 4시
                createOrder(user3, "인천시 미추홀구", "707070", 21000, now.minusMinutes(30), product1, 3, product3, 1), // 30분 전
                createOrder(user3, "인천시 미추홀구", "707070", 11500, now.minusMinutes(10), product2, 1, product4, 1) // 10분 전
        );

        orderRepository.saveAll(orders);
        System.out.println("✅ 주문 데이터 삽입 완료");
    }

    // 주문 생성
    private Order createOrder(User user, String address, String code, int totalPrice, LocalDateTime createdAt,
                              Product product1, int quantity1, Product product2, int quantity2) {

        // 전날 오후 2시 기준으로 주문 상태 설정
        LocalDateTime cutoffTime = LocalDateTime.now().with(LocalTime.of(14, 0)).minusDays(1);
        Status status = createdAt.isBefore(cutoffTime) ? Status.DELIVERED : Status.PENDING;

        // 주문 저장
        Order order = orderRepository.save(
                Order.builder()
                        .user(user)
                        .address(address)
                        .code(code)
                        .totalPrice(totalPrice)
                        .createdAt(createdAt)
                        .status(status)
                        .build()
        );

        // 주문 상품 저장
        orderProductRepository.saveAll(List.of(
                OrderProduct.builder().order(order).product(product1).quantity(quantity1).build(),
                OrderProduct.builder().order(order).product(product2).quantity(quantity2).build()
        ));

        return order;
    }
}
