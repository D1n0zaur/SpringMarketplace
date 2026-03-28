package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.Cart.Cart;
import com.marketplace.SelfPraktik.Entities.CartEntity;
import com.marketplace.SelfPraktik.Entities.ProductEntity;
import com.marketplace.SelfPraktik.Entities.UserEntity;
import com.marketplace.SelfPraktik.Mappers.CartMapper;
import com.marketplace.SelfPraktik.Repositories.CartRepository;
import com.marketplace.SelfPraktik.Repositories.ProductRepository;
import com.marketplace.SelfPraktik.Repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMapper mapper;
    private final CartRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Cart getCart(Long userId) {
        CartEntity entity = repository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        return mapper.toDomain(entity);
    }

    @Transactional
    public Cart addProduct(Long userId, Long productId) {
        CartEntity cart = findOrCreateCart(userId);

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        cart.addProduct(product);
        CartEntity saved = repository.save(cart);

        return mapper.toDomain(saved);
    }

    @Transactional
    public Cart removeProduct(Long userId, Long productId) {
        CartEntity cart = findOrCreateCart(userId);
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        boolean removed = cart.removeProduct(product);
        if (!removed) {
            throw new IllegalArgumentException("Product with id " + productId + " is not in the cart");
        }
        CartEntity saved = repository.save(cart);

        return mapper.toDomain(saved);
    }

    @Transactional
    public void clearCart(Long userId) {
        CartEntity cart = repository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        cart.clear();
        repository.save(cart);
    }

    private CartEntity findOrCreateCart(Long userId) {
        return repository.findByUserId(userId)
                .orElseGet(() -> {
                    UserEntity user = userRepository.findById(userId)
                            .orElseThrow(() -> new EntityNotFoundException("User not found"));

                    CartEntity cart = new CartEntity(user);
                    return repository.save(cart);
                });
    }
}
