package org.oka.kafka.mapper;

import openapi.gen.springbootserver.model.OrderDto;
import org.oka.kafka.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMapper {
    public Order toOrder(final OrderDto orderDto) {
        return Order.builder()
                .name(orderDto.getName())
                .price(new BigDecimal(orderDto.getPrice()))
                .client(orderDto.getClientName())
                .build();
    }

    public OrderDto toOrderDto(final Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.id((int) order.getId());
        orderDto.name(order.getName());
        orderDto.price(order.getPrice().toString());
        orderDto.clientName(order.getClient());
        orderDto.createdDate(order.getCreatedTime().toString());
        orderDto.setStatus(order.getStatus());

        return orderDto;
    }
}
