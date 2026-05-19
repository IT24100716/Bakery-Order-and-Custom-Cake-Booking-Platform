package bake.nest.customcake;

import bake.nest.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomCakeController {

    private final CustomCakeService service;

    // Customer Endpoints
    @PostMapping("/customer/custom-cakes")
    public ResponseEntity<CustomCakeRequestDto> submitRequest(
            @RequestBody CustomCakeRequestDto dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.submitRequest(dto, user.getId()));
    }

    @GetMapping("/customer/custom-cakes")
    public ResponseEntity<List<CustomCakeRequestDto>> getMyRequests(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.getUserRequests(user.getId()));
    }

    @PostMapping("/customer/custom-cakes/{id}/cancel")
    public ResponseEntity<CustomCakeRequestDto> cancelRequest(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.cancelRequest(id, user.getId()));
    }

    @PostMapping("/customer/custom-cakes/{id}/pay")
    public ResponseEntity<CustomCakeRequestDto> payForRequest(
            @PathVariable Long id,
            @RequestBody CustomCakePaymentRequest paymentRequest,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.payForRequest(id, user.getId(), paymentRequest));
    }

    // Admin Endpoints
    @GetMapping("/admin/custom-cakes")
    public ResponseEntity<List<CustomCakeRequestDto>> getAllRequests() {
        return ResponseEntity.ok(service.getAllRequests());
    }

    @PatchMapping("/admin/custom-cakes/{id}/quote")
    public ResponseEntity<CustomCakeRequestDto> quotePrice(
            @PathVariable Long id,
            @RequestParam Double price) {
        return ResponseEntity.ok(service.updateQuote(id, price));
    }

    @PatchMapping("/admin/custom-cakes/{id}/status")
    public ResponseEntity<CustomCakeRequestDto> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}
