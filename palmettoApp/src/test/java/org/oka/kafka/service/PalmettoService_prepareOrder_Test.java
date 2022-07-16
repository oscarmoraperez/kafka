package org.oka.kafka.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oka.kafka.model.Notification;
import org.oka.kafka.model.Order;
import org.oka.kafka.service.PalmettoService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.oka.kafka.config.PalmettoAppConfig.TOPIC_NOTIFICATION;

@ExtendWith(MockitoExtension.class)
public class PalmettoService_prepareOrder_Test {
    @InjectMocks
    PalmettoService palmettoService;
    @Mock
    KafkaTemplate<String, Object> kafkaTemplate;
    @Mock
    ListenableFuture<SendResult<String, Object>> listenableFuture;

    @Test
    public void shouldCallKafkaTemplate() {
        // Given
        when(kafkaTemplate.send(any(), any())).thenReturn(listenableFuture);

        // When
        palmettoService.prepareOrder(Order.builder().build());

        // Then
        verify(kafkaTemplate).send(eq(TOPIC_NOTIFICATION), any(Notification.class));
    }
}
