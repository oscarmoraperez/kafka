package org.oka.kafka.listener;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.oka.kafka.model.Notification;
import org.oka.kafka.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@DirtiesContext
public class OrderListener_listenOrder_IT {
    static KafkaContainer KAFKA;

    static {
        KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
        KAFKA.start();
    }

    @Autowired
    ProducerFactory<String, Object> producerFactory;
    @Autowired
    ConsumerFactory<String, Object> consumerFactory;
    @Autowired
    OrderListener orderListener;

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("kafka.bootstrapAddress", KAFKA::getBootstrapServers);
    }

    @Test
    void shouldSendNotificationThatTheOrderIsReady() {
        // Given - There is a "testing purposes" Kafka producer
        Producer<String, Object> producer = producerFactory.createProducer();
        Consumer<String, Object> consumer = consumerFactory.createConsumer();
        consumer.subscribe(List.of("notification"));
        // And
        Order order = Order.builder().name("Pizza").client("Peter").price(new BigDecimal("123.55")).build();

        // When
        producer.send(new ProducerRecord<>("order", order));

        // Then
        ConsumerRecord<String, Object> record = KafkaTestUtils.getSingleRecord(consumer, "notification");
        assertThat(record).isNotNull();
        Notification notification = (Notification) record.value();
        assertThat(notification.getStatus()).isEqualTo("READY");
    }
}
