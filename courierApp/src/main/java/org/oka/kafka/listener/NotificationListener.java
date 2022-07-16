package org.oka.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oka.kafka.model.Notification;
import org.oka.kafka.service.CourierService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.oka.kafka.config.CourierAppConfig.TOPIC_NOTIFICATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {
    private final CourierService courierService;

    @KafkaListener(topics = TOPIC_NOTIFICATION, groupId = "courierApp")
    public void listenNotification(Notification notification) {
        log.info("Event received: " + notification);

        if (notification.getStatus().equals("READY")) {
            courierService.deliverOrder(notification);
        }
    }
}
