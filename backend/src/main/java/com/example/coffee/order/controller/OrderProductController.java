package com.example.coffee.order.controller;


import com.example.coffee.order.controller.dto.OrderProductResponse;
import com.example.coffee.order.service.OrderProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-product")
@RequiredArgsConstructor
public class OrderProductController {

    private final OrderProductService orderProductService;

    // 상품 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderProductResponse> findAll() {
        return orderProductService.findAll();
    }

    // 상품 단건 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderProductService.delete(id);
    }
}
