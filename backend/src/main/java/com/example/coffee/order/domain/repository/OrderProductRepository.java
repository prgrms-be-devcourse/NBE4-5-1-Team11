package com.example.coffee.order.domain.repository;

import com.example.coffee.order.domain.Order;
import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    void deleteByOrderId(Long orderId);
    List<OrderProduct> findAllByOrder(Order order);
    OrderProduct findByOrderAndProduct(Order order, Product product);
}
