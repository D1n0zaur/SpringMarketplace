package com.marketplace.SelfPraktik.Controllers;

import com.marketplace.SelfPraktik.DTO.Cart.Cart;
import com.marketplace.SelfPraktik.Entities.UserEntity;
import com.marketplace.SelfPraktik.Repositories.UserRepository;
import com.marketplace.SelfPraktik.Services.CartService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    private final UserRepository userRepository;
    private final CartService service;

    @GetMapping
    public ResponseEntity<Cart> getCurrentCart(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Called method getCurrentCart");

        UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return ResponseEntity.ok(service.getCart(user.getId()));
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<Cart> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long productId
    ) {
        log.info("Called method addToCart");

        UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart updated = service.addProduct(user.getId(), productId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Cart> removeFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long productId
    ) {
        log.info("Called method removeFromCart");

        UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart updated = service.removeProduct(user.getId(), productId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Called method clearCart");

        UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        service.clearCart(user.getId());
        return ResponseEntity.noContent().build();
    }
}
