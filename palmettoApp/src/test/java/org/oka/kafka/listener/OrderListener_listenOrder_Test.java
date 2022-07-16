package org.oka.kafka.listener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oka.kafka.model.Order;
import org.oka.kafka.service.PalmettoService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderListener_listenOrder_Test {
    @InjectMocks
    OrderListener orderListener;
    @Mock
    PalmettoService palmettoService;

    @Test
    public void shouldCallPalmettoService() {
        // Given
        Order order = mock(Order.class);

        // When
        orderListener.listenOrder(order);

        // Then
        verify(palmettoService).prepareOrder(order);
    }
}
