package com.example.coffee.order.controller;

import com.example.coffee.order.controller.dto.CreateOrderRequest;
import com.example.coffee.order.controller.dto.OrderResponse;
import com.example.coffee.order.service.OrderService;
import com.example.coffee.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 결제 동시에 유저 저장, 주문 저장
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@RequestBody CreateOrderRequest orderRequest) {
        return orderService.create(orderRequest);
    }

    // 주문 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    // 유저가 자신의 주문 목록 조회
    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> findAllByUser() {
        return orderService.findAllByUser(SecurityUtil.getCurrentUserWithLogin());
    }

    // 유저 이메일로 주문 조회
    /*@GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> findAllByEmail(@RequestParam String email) {
        return orderService.findAllByEmail(email);
    }*/

    // 주문 id로 주문 단건 조회
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    // 주문 id로 주문 단건 삭제
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }

//    // 주문 전체 삭제
//    @DeleteMapping()
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete() {
//        orderService.deleteAll();
//    }
}
