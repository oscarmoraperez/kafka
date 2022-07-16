package org.oka.kafka.controller;

import lombok.RequiredArgsConstructor;
import openapi.gen.springbootserver.api.OrderApi;
import openapi.gen.springbootserver.model.OrderDto;
import org.oka.kafka.mapper.OrderMapper;
import org.oka.kafka.model.Order;
import org.oka.kafka.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController implements OrderApi {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Override
    public ResponseEntity<OrderDto> createOrder(OrderDto orderDto) {
        Order order = this.orderService.createOrder(orderMapper.toOrder(orderDto));

        return ResponseEntity.ok(this.orderMapper.toOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> getOrder(Integer orderId) {
        return ResponseEntity.ok(this.orderMapper.toOrderDto(this.orderService.getOrder(orderId)));
    }
}
