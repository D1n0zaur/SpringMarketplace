package com.marketplace.SelfPraktik.Controllers;

import com.marketplace.SelfPraktik.DTO.Order.Order;
import com.marketplace.SelfPraktik.DTO.Order.OrderCreate;
import com.marketplace.SelfPraktik.Entities.UserEntity;
import com.marketplace.SelfPraktik.Repositories.UserRepository;
import com.marketplace.SelfPraktik.Services.OrderService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final static Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService service;
    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("Called method getAllOrders");

        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        log.info("Called method getOrdersByUser with userId: {}", userId);

        return ResponseEntity.ok(service.getOrdersByUser(userId));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        log.info("Called method getOrderById with orderId: {}", orderId);

        return ResponseEntity.ok(service.getOrderById(orderId));
    }

    @PatchMapping("/{orderId}/deliver")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> deliverOrder(@PathVariable Long orderId) {
        log.info("Called method deliverOrder with orderId: {}", orderId);

        return ResponseEntity.ok(service.updateStatusToDelivered(orderId));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Called method getMyOrders");

        UserEntity currentUser = getCurrentUser(userDetails);
        return ResponseEntity.ok(service.getOrdersByUser(currentUser.getId()));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @Valid @RequestBody OrderCreate request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("Called createOrder");

        UserEntity currentUser = getCurrentUser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrderFromCart(request, currentUser.getId()));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("Called method cancelOrder with orderId: {}", orderId);

        UserEntity currentUser = getCurrentUser(userDetails);
        return ResponseEntity.ok(service.cancelOrder(orderId, currentUser.getId()));
    }

    private UserEntity getCurrentUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
