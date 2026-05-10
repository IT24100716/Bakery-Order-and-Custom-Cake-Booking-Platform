package bake.nest.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(unique = true, length = 20)
    private String sku;

    @Column(nullable = false)
    private Double price;

    private String category;

    @Column(name = "stock_type", length = 20)
    @Builder.Default
    private String stockType = "STOCK";

    private Integer stock;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    private String imageUrl;

    @Column(name = "image_url_2", columnDefinition = "LONGTEXT")
    private String imageUrl2;

    @Column(name = "image_url_3", columnDefinition = "LONGTEXT")
    private String imageUrl3;
}
