package com.example.coffee.order.controller;

import com.example.coffee.order.controller.dto.CreateOrderRequest;

import com.example.coffee.order.controller.dto.OrderResponse;
import com.example.coffee.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Tag(name = "Order API", description = "주문 관련 API")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 결제
    @Operation(summary = "주문 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "주문 생성 성공")
    public void create(@RequestBody CreateOrderRequest request) {
        orderService.create(request);
    }

    // 주문 전체 조회
    @Operation(summary = "주문 목록 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공")
    public List<OrderResponse> findAll() {

        return orderService.findAll();
    }

    // 주문 단건 삭제
    @Operation(summary = "주문 단건 삭제")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "주문 단건 삭제 성공")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

    // 주문 전체 삭제
    @Operation(summary = "주문 전체 삭제")
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "주문 전체 삭제 성공")
    public void delete() {
        orderService.deleteAll();
    }
}
