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
public class OrderController_createOrder_Test {
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

        when(orderMapper.toOrder(any())).thenReturn(Order.builder().client("John Doe").name("Pizza").build());
        // When
        orderController.createOrder(orderDto);

        // Then
        verify(orderMapper).toOrder(orderDto);
    }

    @Test
    void shouldCallOrderService() {
        // Given
        OrderDto orderDto = new OrderDto().clientName("John Doe").name("Pizza");
        Order order = Order.builder().client("John Doe").name("Pizza").build();

        when(orderMapper.toOrder(any())).thenReturn(order);
        // When
        orderController.createOrder(orderDto);

        // Then
        verify(orderService).createOrder(order);
    }
}
