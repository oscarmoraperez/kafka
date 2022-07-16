package org.oka.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.oka.kafka.model.Notification;
import org.oka.kafka.model.Order;
import org.oka.kafka.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.oka.kafka.config.ClientAppConfiguration.TOPIC_ORDER;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @SneakyThrows
    public Order createOrder(Order order) {
        // 0.- Set Correlation ID for tracing purposes
        order.setCorrelationId(UUID.randomUUID());
        order.setStatus("PENDING");

        // 1.- Persist into the ClientApp DB.
        Order persisted = orderRepository.save(order);

        // 2.- Send the message to the broker
        kafkaTemplate.send(TOPIC_ORDER, persisted).addCallback(new FutureCallback());

        return persisted;
    }

    public Order getOrder(long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public void updateOrder(Notification notification) {
        Order order = orderRepository.findById(notification.getId()).orElseThrow();
        order.setStatus(notification.getStatus());
        orderRepository.save(order);
    }
}
