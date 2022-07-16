package org.oka.kafka.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oka.kafka.model.Order;
import org.oka.kafka.repository.OrderRepository;
import org.oka.kafka.service.OrderService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.oka.kafka.config.ClientAppConfiguration.TOPIC_ORDER;

@ExtendWith(MockitoExtension.class)
public class OrderService_createOrder_Test {
    @InjectMocks
    OrderService orderService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    KafkaTemplate<String, Object> kafkaTemplate;
    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;
    @Mock
    ListenableFuture<SendResult<String, Object>> listenableFuture;

    @Test
    void shouldCallOrderRepository() {
        // Given
        Order order = mock(Order.class);
        when(orderRepository.save(any())).thenReturn(order);
        when(kafkaTemplate.send(any(), any())).thenReturn(listenableFuture);

        // When
        orderService.createOrder(order);

        // Then
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order order1 = orderArgumentCaptor.getValue();
        assertThat(order1.getId()).isNotNull();
        verify(order1).setStatus("PENDING");
    }

    @Test
    void shouldCallKafkaTemplate() {
        // Given
        Order order = mock(Order.class);
        when(orderRepository.save(any())).thenReturn(order);
        when(kafkaTemplate.send(any(), any())).thenReturn(listenableFuture);

        // When
        orderService.createOrder(order);

        // Then
        verify(kafkaTemplate).send(TOPIC_ORDER, order);
    }
}
