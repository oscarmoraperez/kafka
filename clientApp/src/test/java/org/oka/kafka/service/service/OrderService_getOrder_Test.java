package org.oka.kafka.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oka.kafka.model.Order;
import org.oka.kafka.repository.OrderRepository;
import org.oka.kafka.service.OrderService;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderService_getOrder_Test {
    @InjectMocks
    OrderService orderService;
    @Mock
    OrderRepository orderRepository;

    @Test
    void shouldCallOrderRepository() {
        // Given

        when(orderRepository.findById(any())).thenReturn(Optional.of(Order.builder().build()));
        // When
        orderService.getOrder(1);

        // Then
        verify(orderRepository).findById(1L);
    }
}
