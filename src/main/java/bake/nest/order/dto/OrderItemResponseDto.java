package bake.nest.order.dto;

import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long id;
    private Long productId;
    private String productName;
    private String productImageUrl;
    private Integer quantity;
    private Double price;
}
