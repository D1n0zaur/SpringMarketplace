package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.Order.Order;
import com.marketplace.SelfPraktik.DTO.Order.OrderCreate;
import com.marketplace.SelfPraktik.Entities.CartEntity;
import com.marketplace.SelfPraktik.Entities.Enums.OrderStatus;
import com.marketplace.SelfPraktik.Entities.Enums.UserRole;
import com.marketplace.SelfPraktik.Entities.OrderEntity;
import com.marketplace.SelfPraktik.Mappers.OrderMapper;
import com.marketplace.SelfPraktik.Repositories.CartRepository;
import com.marketplace.SelfPraktik.Repositories.OrderRepository;

import com.marketplace.SelfPraktik.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    private final CartService cartService;

    private final OrderMapper mapper;

    public List<Order> getAllOrders() {
        List<OrderEntity> allEntities = repository.findAll();

        return allEntities.stream()
                .map(mapper::toDomain).toList();
    }

    public List<Order> getOrdersByUser(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }
        List<OrderEntity> orders = repository.findByUserId(userId);

        return orders.stream()
                .map(mapper::toDomain).toList();
    }

    public Order getOrderById(Long orderId, Long currentUserId) {
        OrderEntity order = repository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(currentUserId) && !isAdmin(currentUserId)) {
            throw new AccessDeniedException("You can only view your own orders");
        }

        return mapper.toDomain(order);
    }

    @Transactional
    public Order createOrderFromCart(OrderCreate request, Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart by user with id=" + userId + " not found"));

        if(cart.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Can not create order from empty cart");
        }

        OrderEntity order = new OrderEntity(
                cart,
                request.address(),
                LocalDate.now(),
                OrderStatus.PAID
        );

        OrderEntity saved = repository.save(order);
        cartService.clearCart(userId);

        return mapper.toDomain(saved);
    }

    @Transactional
    public Order updateStatusToDelivered(Long orderId) {
        OrderEntity entity = repository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if(entity.getStatus() != OrderStatus.PAID) {
            throw new IllegalArgumentException("Order must be in PAID status for this operation");
        }

        entity.setStatus(OrderStatus.DELIVERED);
        OrderEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    @Transactional
    public Order cancelOrder(Long orderId, Long userId) {
        OrderEntity entity = repository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if(!entity.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You can`t cancel not your order");
        }

        if(entity.getStatus() != OrderStatus.PAID) {
            throw new IllegalArgumentException("Order must be in PAID status for this operation");
        }

        entity.setStatus(OrderStatus.CANCELED);
        OrderEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    private boolean isAdmin(Long userId) {
        return userRepository.findById(userId)
                .map(user -> user.getRole() == UserRole.ADMIN)
                .orElse(false);
    }
}
