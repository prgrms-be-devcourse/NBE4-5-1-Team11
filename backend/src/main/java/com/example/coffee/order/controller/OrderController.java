package com.example.coffee.order.controller;

import com.example.coffee.order.controller.dto.CreateOrderRequest;
import com.example.coffee.order.domain.Order;
import com.example.coffee.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 결제
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateOrderRequest request) {
        orderService.create(request);
    }

    // 주문 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findAll() {
        return orderService.findAll();
    }
}
