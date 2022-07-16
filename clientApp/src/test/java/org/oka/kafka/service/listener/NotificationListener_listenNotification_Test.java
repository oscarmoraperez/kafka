package org.oka.kafka.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oka.kafka.listener.NotificationListener;
import org.oka.kafka.model.Notification;
import org.oka.kafka.service.OrderService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationListener_listenNotification_Test {
    @InjectMocks
    NotificationListener notificationListener;
    @Mock
    ObjectMapper objectMapper;
    @Mock
    OrderService orderService;

    @Test
    public void shouldUpdateOrder() throws JsonProcessingException {
        // Given
        Notification notification = Notification.builder().build();

        // When
        notificationListener.listenNotification(notification);

        // Then
        verify(orderService).updateOrder(notification);
    }
}
