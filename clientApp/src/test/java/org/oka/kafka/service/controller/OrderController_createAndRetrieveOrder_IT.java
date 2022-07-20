package org.oka.kafka.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import openapi.gen.springbootserver.model.OrderDto;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.oka.kafka.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@DirtiesContext
public class OrderController_createAndRetrieveOrder_IT {
    static KafkaContainer KAFKA;

    static {
        KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
        KAFKA.start();
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ConsumerFactory<String, Object> consumerFactory;

    private ObjectMapper objectMapper = new ObjectMapper();

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("kafka.bootstrapAddress", KAFKA::getBootstrapServers);
    }

    @Test
    void shouldPersistOrder() throws Exception {
        // Given - There is a "testing purposes" Kafka consumer
        Consumer<String, Object> consumer = consumerFactory.createConsumer();
        consumer.subscribe(List.of("order"));
        // And
        OrderDto orderDto = new OrderDto().clientName("John Doe").name("Pizza").price("12.55");

        // When
        ResultActions resultActions = this.mockMvc
                .perform(post("/order")
                        .header("content-type", "application/json")
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print());

        // Then
        String response = resultActions.andReturn().getResponse().getContentAsString();
        OrderDto readValue = objectMapper.readValue(response, OrderDto.class);
        assertThat(readValue.getClientName()).isEqualTo("John Doe");
        assertThat(readValue.getName()).isEqualTo("Pizza");
        assertThat(readValue.getPrice()).isEqualTo("12.55");
        // And - The "testing purposes" consumer has received the message from Client App
        ConsumerRecord<String, Object> record = KafkaTestUtils.getSingleRecord(consumer, "order");
        assertThat(record).isNotNull();
        Order order = (Order) record.value();
        assertThat(order.getName()).isEqualTo("Pizza");
    }
}
