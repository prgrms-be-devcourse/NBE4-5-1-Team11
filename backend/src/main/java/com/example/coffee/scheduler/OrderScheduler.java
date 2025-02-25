package com.example.coffee.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.coffee.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderScheduler {

	private final OrderService orderService;

	@Scheduled(cron = "0 0 14 * * *")
	public void execute() {
		orderService.updateState();
	}
}
