package org.oka.kafka.service.mapper;

import openapi.gen.springbootserver.model.OrderDto;
import org.junit.jupiter.api.Test;
import org.oka.kafka.mapper.OrderMapper;
import org.oka.kafka.model.Order;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderMapper_toOder_Test {
    OrderMapper orderMapper = new OrderMapper();

    @Test
    public void shouldMapToOrder() {
        // Given
        OrderDto orderDto = new OrderDto();
        orderDto.name("name");
        orderDto.price("123");
        orderDto.clientName("John");

        // When
        Order order = orderMapper.toOrder(orderDto);

        // Then
        assertThat(order.getName()).isEqualTo("name");
        assertThat(order.getPrice()).isEqualTo("123");
        assertThat(order.getClient()).isEqualTo("John");
    }
}
