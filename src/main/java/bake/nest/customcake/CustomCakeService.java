package bake.nest.customcake;

import bake.nest.user.User;
import bake.nest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomCakeService {

    private final CustomCakeRequestRepository repository;
    private final UserRepository userRepository;

    @Transactional
    public CustomCakeRequestDto submitRequest(CustomCakeRequestDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CustomCakeRequest request = CustomCakeRequest.builder()
                .user(user)
                .flavor(dto.getFlavor())
                .size(dto.getSize())
                .message(dto.getMessage())
                .referenceImageUrl(dto.getReferenceImageUrl())
                .includeCandles(dto.getIncludeCandles())
                .toppings(dto.getToppings())
                .themeColor(dto.getThemeColor())
                .themeColor2(dto.getThemeColor2())
                .themeColor3(dto.getThemeColor3())
                .cakeWording(dto.getCakeWording())
                .neededDate(dto.getNeededDate())
                .status("PENDING")
                .build();

        CustomCakeRequest saved = repository.save(request);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<CustomCakeRequestDto> getUserRequests(Long userId) {
        return repository.findByUserIdOrderByIdDesc(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomCakeRequestDto> getAllRequests() {
        return repository.findAllByOrderByIdDesc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomCakeRequestDto updateQuote(Long id, Double price) {
        CustomCakeRequest request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setQuotedPrice(price);
        request.setStatus("QUOTED");
        return mapToDto(repository.save(request));
    }

    @Transactional
    public CustomCakeRequestDto updateStatus(Long id, String status) {
        CustomCakeRequest request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status);
        return mapToDto(repository.save(request));
    }

    @Transactional
    public CustomCakeRequestDto cancelRequest(Long id, Long userId) {
        CustomCakeRequest request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to cancel this request");
        }

        // Only PENDING or QUOTED requests can be cancelled by customer
        if (!"PENDING".equalsIgnoreCase(request.getStatus()) && !"QUOTED".equalsIgnoreCase(request.getStatus())) {
            throw new RuntimeException("Request cannot be cancelled as it is already " + request.getStatus());
        }

        request.setStatus("CANCELLED");
        return mapToDto(repository.save(request));
    }

    private CustomCakeRequestDto mapToDto(CustomCakeRequest request) {
        return CustomCakeRequestDto.builder()
                .id(request.getId())
                .userId(request.getUser().getId())
                .customerName(request.getUser().getName())
                .flavor(request.getFlavor())
                .size(request.getSize())
                .message(request.getMessage())
                .referenceImageUrl(request.getReferenceImageUrl())
                .status(request.getStatus())
                .includeCandles(request.getIncludeCandles())
                .toppings(request.getToppings())
                .themeColor(request.getThemeColor())
                .themeColor2(request.getThemeColor2())
                .themeColor3(request.getThemeColor3())
                .cakeWording(request.getCakeWording())
                .neededDate(request.getNeededDate())
                .quotedPrice(request.getQuotedPrice())
                .paymentMethod(request.getPaymentMethod())
                .paymentSlipUrl(request.getPaymentSlipUrl())
                .shippingAddress(request.getShippingAddress())
                .phone(request.getPhone())
                .deliveryMethod(request.getDeliveryMethod())
                .deliveryFee(request.getDeliveryFee())
                .createdAt(request.getCreatedAt())
                .build();
    }

    @Transactional
    public CustomCakeRequestDto payForRequest(Long id, Long userId, CustomCakePaymentRequest paymentDto) {
        CustomCakeRequest request = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Custom cake request not found"));

        if (!request.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to modify this request");
        }

        if (!"QUOTED".equalsIgnoreCase(request.getStatus())) {
            throw new RuntimeException("Request must be QUOTED to proceed with payment");
        }

        request.setPaymentMethod(paymentDto.getPaymentMethod());
        request.setPaymentSlipUrl(paymentDto.getPaymentSlipUrl());
        request.setShippingAddress(paymentDto.getShippingAddress());
        request.setPhone(paymentDto.getPhone());
        request.setDeliveryMethod(paymentDto.getDeliveryMethod());
        request.setDeliveryFee(paymentDto.getDeliveryFee());
        
        if ("CARD".equalsIgnoreCase(paymentDto.getPaymentMethod())) {
            request.setStatus("APPROVED");
        } else {
            request.setStatus("ACCEPTED");
        }

        CustomCakeRequest saved = repository.save(request);
        return mapToDto(saved);
    }
}
