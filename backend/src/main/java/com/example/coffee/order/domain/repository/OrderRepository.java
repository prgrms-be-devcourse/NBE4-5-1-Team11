package com.example.coffee.order.domain.repository;

import com.example.coffee.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
