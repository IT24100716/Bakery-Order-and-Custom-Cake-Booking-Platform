package bake.nest.order.dto;

import bake.nest.order.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private Double totalAmount;
    private OrderStatus status;
    private String paymentStatus;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDto> items;
}
