package bake.nest.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    @NotEmpty
    @Valid
    private List<OrderItemRequestDto> items;

    @NotNull
    private String paymentMethod;
    
    private String shippingAddress;
    
    private String phone;

    private String deliveryMethod;

    private Double deliveryFee;

    private String paymentSlipUrl;
}
