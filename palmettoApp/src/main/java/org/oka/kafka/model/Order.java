package org.oka.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
public class Order {
    private long id;
    private UUID correlationId;
    private String name;
    private String client;
    private BigDecimal price;
    private LocalDateTime createdTime;
    private String status;
}

