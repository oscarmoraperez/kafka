package org.oka.kafka.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oka.kafka.model.Notification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierService_deliverOrder_Test {
    @InjectMocks
    CourierService courierService;
    @Mock
    KafkaTemplate<String, Object> kafkaTemplate;
    @Mock
    ListenableFuture<SendResult<String, Object>> listenableFuture;

    @Test
    public void shouldCallKafkaTemplate() {
        // Given
        when(kafkaTemplate.send(any(), any())).thenReturn(listenableFuture);

        // When
        courierService.deliverOrder(Notification.builder().build());

        // Then
        verify(kafkaTemplate).send(anyString(), eq(Notification.class));
    }
}
