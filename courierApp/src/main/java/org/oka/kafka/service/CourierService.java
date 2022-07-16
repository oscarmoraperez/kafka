package org.oka.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.oka.kafka.model.Notification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static org.oka.kafka.config.CourierAppConfig.TOPIC_NOTIFICATION;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @SneakyThrows
    public void deliverOrder(Notification notification) {
        // Dummy wait to represent the required time to deliver the order
        Thread.sleep(5000);

        Notification notificationDelivered = Notification.builder()
                .id(notification.getId())
                .correlationId(notification.getCorrelationId())
                .status("DELIVERED")
                .build();

        kafkaTemplate.send(TOPIC_NOTIFICATION, notificationDelivered).addCallback(new FutureCallback());
    }
}
