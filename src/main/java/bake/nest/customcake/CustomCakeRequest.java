package bake.nest.customcake;

import bake.nest.user.User;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "quoted_price")
    private Double quotedPrice;
}
