package bake.nest.customcake;

import bake.nest.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "custom_cake_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomCakeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String flavor;

    private String size;

    @Column(length = 1000)
    private String message;

    @Column(name = "reference_image_url")
    private String referenceImageUrl;

    @Column(nullable = false)
    private String status;

    @Column(name = "include_candles")
    private Boolean includeCandles;

    @Column(length = 500)
    private String toppings;

    @Column(name = "theme_color")
    private String themeColor;

    @Column(name = "theme_color_2")
    private String themeColor2;

    @Column(name = "theme_color_3")
    private String themeColor3;

    @Column(name = "cake_wording")
    private String cakeWording;

    @Column(name = "needed_date")
    private String neededDate;

    @Column(name = "quoted_price")
    private Double quotedPrice;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_slip_url")
    private String paymentSlipUrl;

    @Column(name = "shipping_address")
    private String shippingAddress;

    private String phone;

    @Column(name = "delivery_method")
    private String deliveryMethod;

    @Column(name = "delivery_fee")
    private Double deliveryFee;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
