package org.oka.kafka.service.mapper;

import openapi.gen.springbootserver.model.OrderDto;
import org.junit.jupiter.api.Test;
import org.oka.kafka.mapper.OrderMapper;
import org.oka.kafka.model.Order;

import java.math.BigDecimal;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderMapper_toOderDto_Test {
    OrderMapper orderMapper = new OrderMapper();

    @Test
    public void shouldMapToOrderDto() {
        // Given
        Order order = Order.builder().name("name").price(new BigDecimal("123")).client("John").createdTime(now()).build();

        // When
        OrderDto orderDto = orderMapper.toOrderDto(order);

        // Then
        assertThat(orderDto.getName()).isEqualTo("name");
        assertThat(orderDto.getPrice()).isEqualTo("123");
        assertThat(order.getClient()).isEqualTo("John");
    }
}
