package com.example.coffee.order.domain.repository;

import com.example.coffee.order.domain.Order;
import com.example.coffee.user.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user, Sort createdAt);
}
