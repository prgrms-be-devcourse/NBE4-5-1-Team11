package com.example.coffee.order.controller;

import com.example.coffee.order.controller.dto.OrderResponse;
import com.example.coffee.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "주문 관련 API")
public class AdminOrderController {

    private final OrderService orderService;


    @Operation(summary = "주문 전체 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @Operation(summary = "주문 단건 조회 - userId")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @Operation(summary = "주문 단건 삭제")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}
