package org.oka.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.oka.kafka.model.Notification;
import org.oka.kafka.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static org.oka.kafka.config.PalmettoAppConfig.TOPIC_NOTIFICATION;

@Service
@RequiredArgsConstructor
public class PalmettoService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @SneakyThrows
    public void prepareOrder(final Order order) {
        // Dummy wait to represent the required time to prepare the order
        Thread.sleep(5000);

        Notification notification = Notification.builder()
                .id(order.getId())
                .correlationId(order.getCorrelationId())
                .status("READY")
                .build();

        kafkaTemplate.send(TOPIC_NOTIFICATION, notification).addCallback(new FutureCallback());
    }
}
