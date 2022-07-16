package org.oka.kafka.service.controller;

import openapi.gen.springbootserver.model.OrderDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oka.kafka.controller.OrderController;
import org.oka.kafka.mapper.OrderMapper;
import org.oka.kafka.model.Order;
import org.oka.kafka.service.OrderService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderController_getOrder_Test {
    @InjectMocks
    OrderController orderController;
    @Mock
    OrderService orderService;
    @Mock
    OrderMapper orderMapper;

    @Test
    void shouldCallOrderMapper() {
        // Given
        OrderDto orderDto = new OrderDto().clientName("John Doe").name("Pizza");
        Order order = new Order();
        order.setStatus("READY");
        when(orderService.getOrder(1)).thenReturn(order);
        when(orderMapper.toOrderDto(any())).thenReturn(orderDto);

        // When
        orderController.getOrder(1);

        // Then
        verify(orderMapper).toOrderDto(order);
    }

    @Test
    void shouldCallOrderService() {
        // Given
        OrderDto orderDto = new OrderDto().clientName("John Doe").name("Pizza");

        when(orderMapper.toOrderDto(any())).thenReturn(orderDto);
        // When
        orderController.getOrder(1);

        // Then
        verify(orderService).getOrder(1);
    }
}
