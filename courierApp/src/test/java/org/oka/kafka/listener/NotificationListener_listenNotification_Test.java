package org.oka.kafka.listener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oka.kafka.model.Notification;
import org.oka.kafka.service.CourierService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationListener_listenNotification_Test {
    @InjectMocks
    NotificationListener notificationListener;
    @Mock
    CourierService courierService;

    @Test
    public void shouldCallCourierService_WhenNotificationIsREADY() {
        // Given
        Notification notification = mock(Notification.class);
        when(notification.getStatus()).thenReturn("READY");

        // When
        notificationListener.listenNotification(notification);

        // Then
        verify(courierService).deliverOrder(any(Notification.class));
    }

    @Test
    public void shouldNotCallCourierService_WhenNotificationIsNotREADY() {
        // Given
        Notification notification = mock(Notification.class);
        when(notification.getStatus()).thenReturn("ANOTHER_STATE");

        // When
        notificationListener.listenNotification(notification);

        // Then
        verify(courierService, never()).deliverOrder(any(Notification.class));
    }
}
