package bake.nest.order;

import bake.nest.order.dto.OrderRequestDto;
import bake.nest.order.dto.OrderResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'SUPER_ADMIN', 'PRODUCT_ADMIN', 'ORDER_ADMIN', 'USER_ADMIN')")
    public ResponseEntity<OrderResponseDto> placeOrder(@Valid @RequestBody OrderRequestDto requestDto) {
        return ResponseEntity.ok(orderService.placeOrder(requestDto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'SUPER_ADMIN', 'PRODUCT_ADMIN', 'ORDER_ADMIN', 'USER_ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'SUPER_ADMIN', 'PRODUCT_ADMIN', 'ORDER_ADMIN', 'USER_ADMIN')")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('USER', 'SUPER_ADMIN', 'PRODUCT_ADMIN', 'ORDER_ADMIN', 'USER_ADMIN')")
    public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}
