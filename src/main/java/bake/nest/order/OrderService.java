package bake.nest.order;

import bake.nest.order.dto.OrderItemRequestDto;
import bake.nest.order.dto.OrderItemResponseDto;
import bake.nest.order.dto.OrderRequestDto;
import bake.nest.order.dto.OrderResponseDto;
import bake.nest.product.Product;
import bake.nest.product.ProductRepository;
import bake.nest.user.User;
import bake.nest.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto requestDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        double totalAmount = 0.0;
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .totalAmount(0.0)
                .paymentMethod(requestDto.getPaymentMethod())
                .shippingAddress(requestDto.getShippingAddress())
                .phone(requestDto.getPhone())
                .deliveryMethod(requestDto.getDeliveryMethod())
                .deliveryFee(requestDto.getDeliveryFee() != null ? requestDto.getDeliveryFee() : 0.0)
                .paymentSlipUrl(requestDto.getPaymentSlipUrl())
                .paymentStatus(calculateInitialPaymentStatus(requestDto.getPaymentMethod()))
                .build();

        // Need to add items first to associate them
        for (OrderItemRequestDto itemRequest : requestDto.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));

            if (!product.getActive() || product.getDeleted()) {
                throw new RuntimeException("Product is no longer available: " + product.getName());
            }

            if ("STOCK".equalsIgnoreCase(product.getStockType())) {
                if (product.getStock() == null || product.getStock() < itemRequest.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }
                product.setStock(product.getStock() - itemRequest.getQuantity());
                productRepository.save(product);
            }

            double itemTotal = product.getPrice() * itemRequest.getQuantity();
            totalAmount += itemTotal;

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice())
                    .build();

            order.getItems().add(orderItem);
        }

        order.setTotalAmount(totalAmount + order.getDeliveryFee());
        Order savedOrder = orderRepository.save(order);

        return convertToResponseDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getMyOrders() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDto updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(newStatus);
        Order savedOrder = orderRepository.save(order);
        return convertToResponseDto(savedOrder);
    }

    @Transactional
    public OrderResponseDto updatePaymentStatus(Long id, String paymentStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setPaymentStatus(paymentStatus);
        Order savedOrder = orderRepository.save(order);
        return convertToResponseDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Ensure user owns this order
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to view this order");
        }

        return convertToResponseDto(order);
    }

    @Transactional
    public OrderResponseDto cancelOrder(Long id) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Ensure user owns this order
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to cancel this order");
        }

        // Only PENDING orders can be cancelled by customer
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Order cannot be cancelled as it is already " + order.getStatus());
        }

        // Restore stock
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            if ("STOCK".equalsIgnoreCase(product.getStockType())) {
                if (product.getStock() != null) {
                    product.setStock(product.getStock() + item.getQuantity());
                    productRepository.save(product);
                }
            }
        }

        order.setStatus(OrderStatus.CANCELLED);
        Order savedOrder = orderRepository.save(order);

        return convertToResponseDto(savedOrder);
    }

    private OrderResponseDto convertToResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPaymentSlipUrl(order.getPaymentSlipUrl());
        dto.setCreatedAt(order.getCreatedAt());
        
        // Include customer info for admin views
        if (order.getUser() != null) {
            dto.setCustomerName(order.getUser().getName());
            dto.setCustomerEmail(order.getUser().getEmail());
        }

        List<OrderItemResponseDto> itemDtos = order.getItems().stream().map(item -> {
            OrderItemResponseDto itemDto = new OrderItemResponseDto();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setProductImageUrl(item.getProduct().getImageUrl());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }

    private String calculateInitialPaymentStatus(String method) {
        if ("COD".equalsIgnoreCase(method))
            return "PENDING";
        if ("CARD".equalsIgnoreCase(method))
            return "PAID";
        if ("BANK_TRANSFER".equalsIgnoreCase(method))
            return "WAITING_APPROVAL";
        return "PENDING";
    }
}
