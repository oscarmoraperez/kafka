package org.oka.kafka.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import openapi.gen.springbootserver.model.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderController_createAndRetrieveOrder_IT {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldPersistOrder() throws Exception {
        // Given
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
    }
}
