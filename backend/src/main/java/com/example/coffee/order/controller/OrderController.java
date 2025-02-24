package com.example.coffee.order.controller;

import com.example.coffee.order.controller.dto.CreateOrderRequest;
import com.example.coffee.order.controller.dto.OrderResponse;
import com.example.coffee.order.service.OrderService;
import com.example.coffee.security.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "주문 관련 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateOrderRequest orderRequest) {
        orderService.create(orderRequest);
    }

    @Operation(summary = "유저별 주문 목록 조회: 이것만 안됩니다 ㅜㅜ")
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> findAllByUser() {
        return orderService.findAllByUser(SecurityUtil.getCurrentUserWithLogin());
    }
}
