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
    private String paymentMethod;
    private String paymentSlipUrl;
    private LocalDateTime createdAt;
    private String customerName;
    private String customerEmail;
    private List<OrderItemResponseDto> items;
}
