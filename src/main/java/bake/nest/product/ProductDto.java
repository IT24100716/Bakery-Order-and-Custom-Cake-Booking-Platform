package bake.nest.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;

    @NotBlank
    private String name;

    private String description;
    
    private String sku;

    @NotNull
    @PositiveOrZero
    private Double price;

    private String category;

    private String stockType;

    private Integer stock;

    private Boolean active;
    private Boolean deleted;

    private String imageUrl;
    private String imageUrl2;
    private String imageUrl3;
}
