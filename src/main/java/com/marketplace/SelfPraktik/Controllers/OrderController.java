package com.marketplace.SelfPraktik.Controllers;

import com.marketplace.SelfPraktik.DTO.Order.Order;
import com.marketplace.SelfPraktik.DTO.Order.OrderCreate;
import com.marketplace.SelfPraktik.DTO.Order.OrderUpdate;
import com.marketplace.SelfPraktik.Services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final static Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService service;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("Called method getAllOrders");

        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getAllOrdersByUser(@PathVariable Long userId) {
        log.info("Called method getOrdersByUser with userId: {}", userId);

        return ResponseEntity.ok(service.getAllOrdersByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        log.info("Called method getOrderById with id: {}", id);

        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreate request) {
        log.info("Called method createOrder");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderUpdate request
            ) {
        log.info("Called method updateOrder with id: {}", id);

        Order updated = service.updateOrder(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.info("Called method deleteOrder with id: {}", id);

        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
