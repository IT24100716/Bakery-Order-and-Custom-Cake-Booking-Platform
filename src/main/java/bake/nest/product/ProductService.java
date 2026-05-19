package bake.nest.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> getAllProducts() {
        return productRepository.findByDeletedFalse().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getActiveProducts() {
        return productRepository.findByActiveTrueAndDeletedFalse().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        return convertToDto(productRepository.save(product));
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setSku(productDto.getSku());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setCategory(productDto.getCategory());
        existingProduct.setStockType(productDto.getStockType());
        existingProduct.setStock(productDto.getStock());
        existingProduct.setActive(productDto.getActive() != null ? productDto.getActive() : true);
        existingProduct.setImageUrl(productDto.getImageUrl());
        existingProduct.setImageUrl2(productDto.getImageUrl2());
        existingProduct.setImageUrl3(productDto.getImageUrl3());

        return convertToDto(productRepository.save(existingProduct));
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setDeleted(true);
        product.setActive(false);
        productRepository.save(product);
    }

    public List<ProductDto> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query).stream()
                .filter(p -> p.getActive() && !p.getDeleted())
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setSku(product.getSku());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setStockType(product.getStockType());
        dto.setStock(product.getStock());
        dto.setActive(product.getActive());
        dto.setDeleted(product.getDeleted());
        dto.setImageUrl(product.getImageUrl());
        dto.setImageUrl2(product.getImageUrl2());
        dto.setImageUrl3(product.getImageUrl3());
        return dto;
    }

    private Product convertToEntity(ProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .sku(dto.getSku())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .stockType(dto.getStockType() != null ? dto.getStockType() : "STOCK")
                .stock(dto.getStock())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .imageUrl(dto.getImageUrl())
                .imageUrl2(dto.getImageUrl2())
                .imageUrl3(dto.getImageUrl3())
                .build();
    }
}
