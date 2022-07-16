package org.oka.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oka.kafka.model.Order;
import org.oka.kafka.service.PalmettoService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.oka.kafka.config.PalmettoAppConfig.TOPIC_ORDER;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderListener {
    private final PalmettoService palmettoService;

    @KafkaListener(topics = TOPIC_ORDER, groupId = "palmettoApp")
    public void listenOrder(Order order) {
        log.info("Event received: " + order);

        palmettoService.prepareOrder(order);
    }
}
