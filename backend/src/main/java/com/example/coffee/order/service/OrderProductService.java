package com.example.coffee.order.service;

import com.example.coffee.order.domain.OrderProduct;
import com.example.coffee.order.domain.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    // 상품 전체 조회
    public List<OrderProduct> findAll() {
        return orderProductRepository.findAll();
    }

    // 상품 단건 삭제
    public void delete(Long id) {
        orderProductRepository.deleteById(id);
    }

    // 상품 전체 삭제
    public void deleteAll() {
        orderProductRepository.deleteAll();
    }

}
