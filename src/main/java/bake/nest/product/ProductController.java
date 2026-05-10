package bake.nest.product;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/public/products")
    public List<ProductDto> getActiveProducts() {
        return productService.getActiveProducts();
    }

    @GetMapping("/admin/products")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'PRODUCT_ADMIN')")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/public/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/public/products/search")
    public List<ProductDto> searchProducts(@RequestParam String query) {
        return productService.searchProducts(query);
    }

    @PostMapping("/admin/products")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'PRODUCT_ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @PutMapping("/admin/products/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'PRODUCT_ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("/admin/products/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'PRODUCT_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
