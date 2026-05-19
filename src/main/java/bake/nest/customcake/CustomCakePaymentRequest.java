package bake.nest.customcake;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomCakePaymentRequest {
    private String paymentMethod;
    private String shippingAddress;
    private String phone;
    private String deliveryMethod;
    private Double deliveryFee;
    private String paymentSlipUrl;
}
