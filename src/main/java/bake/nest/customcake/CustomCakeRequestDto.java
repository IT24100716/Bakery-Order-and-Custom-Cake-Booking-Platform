package bake.nest.customcake;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomCakeRequestDto {
    private Long id;
    private Long userId;
    private String customerName;
    private String flavor;
    private String size;
    private String message;
    private String referenceImageUrl;
    private String status;
    private Boolean includeCandles;
    private String toppings;
    private String themeColor;
    private String themeColor2;
    private String themeColor3;
    private String cakeWording;
    private String neededDate;
    private Double quotedPrice;
    private String paymentMethod;
    private String paymentSlipUrl;
    private String shippingAddress;
    private String phone;
    private String deliveryMethod;
    private Double deliveryFee;
    private LocalDateTime createdAt;
}
