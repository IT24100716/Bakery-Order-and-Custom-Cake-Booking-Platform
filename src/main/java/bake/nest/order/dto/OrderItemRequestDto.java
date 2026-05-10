package bake.nest.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemRequestDto {
    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;
}
