package com.example.coffee.order.service;

import com.example.coffee.order.controller.dto.OrderProductResponse;
import com.example.coffee.order.domain.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    // 주문 상품 전체 조회
    public List<OrderProductResponse> findAll() {
        return orderProductRepository.findAll().stream()
                .map(OrderProductResponse::from)
                .toList();
    }

    // 주문 상품 단건 삭제
    public void delete(Long id) {
        orderProductRepository.deleteById(id);
    }
}
