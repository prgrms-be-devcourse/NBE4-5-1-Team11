package com.example.coffee.order.controller;

import com.example.coffee.order.controller.dto.CreateOrderRequest;

import com.example.coffee.order.controller.dto.OrderResponse;
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
    public List<OrderResponse> findAll() {

        return orderService.findAll();
    }

    // 주문 단건 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

    // 주문 전체 삭제
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        orderService.deleteAll();
    }
}
