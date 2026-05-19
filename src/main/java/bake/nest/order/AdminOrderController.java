package bake.nest.order;

import bake.nest.order.dto.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ORDER_ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ORDER_ADMIN')")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        
        String newStatusStr = payload.get("status");
        if (newStatusStr == null) {
            return ResponseEntity.badRequest().build();
        }
        
        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(newStatusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(orderService.updateOrderStatus(id, newStatus));
    }

    @PatchMapping("/{id}/payment-status")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ORDER_ADMIN')")
    public ResponseEntity<OrderResponseDto> updatePaymentStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        
        String newPaymentStatus = payload.get("status");
        if (newPaymentStatus == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(orderService.updatePaymentStatus(id, newPaymentStatus));
    }
}
