package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.Order.Order;
import com.marketplace.SelfPraktik.DTO.Order.OrderCreate;
import com.marketplace.SelfPraktik.Entities.CartEntity;
import com.marketplace.SelfPraktik.Entities.Enums.OrderStatus;
import com.marketplace.SelfPraktik.Entities.Enums.UserRole;
import com.marketplace.SelfPraktik.Entities.OrderEntity;
import com.marketplace.SelfPraktik.Entities.ProductEntity;
import com.marketplace.SelfPraktik.Entities.UserEntity;
import com.marketplace.SelfPraktik.Mappers.OrderMapper;
import com.marketplace.SelfPraktik.Repositories.CartRepository;
import com.marketplace.SelfPraktik.Repositories.OrderRepository;
import com.marketplace.SelfPraktik.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartService cartService;
    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private UserEntity user;
    private CartEntity cart;
    private OrderEntity order;
    private OrderCreate orderCreate;
    private final Long userId = 1L;
    private final Long orderId = 100L;
    private final String address = "Delivery Address 10-20";

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(userId);
        user.setUsername("testUser");

        ProductEntity product = new ProductEntity();
        product.setId(10L);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));

        cart = new CartEntity(user);
        cart.setId(1L);
        cart.getProducts().add(product);
        cart.setTotalPrice(BigDecimal.valueOf(100));

        order = new OrderEntity();
        order.setId(orderId);
        order.setUser(user);
        order.setOrderPrice(BigDecimal.valueOf(100));
        order.setStatus(OrderStatus.PAID);
        order.setDate(LocalDate.now());
        order.setAddress(address);

        orderCreate = new OrderCreate(address);
    }

    private Order buildOrderDto(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getAddress(),
                null,
                null,
                entity.getOrderPrice(),
                entity.getDate(),
                entity.getStatus()
        );
    }

    @Test
    void createOrderFromCart_ShouldCreateOrder_WhenCartExistsAndNotEmpty() {
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderMapper.toDomain(any(OrderEntity.class))).thenAnswer(invocation -> buildOrderDto(invocation.getArgument(0)));

        Order result = orderService.createOrderFromCart(orderCreate, userId);

        assertThat(result).isNotNull();
        assertThat(result.orderPrice()).isEqualByComparingTo(BigDecimal.valueOf(100));
        verify(cartService).clearCart(userId);
    }

    @Test
    void createOrderFromCart_ShouldThrowException_WhenCartNotFound() {
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.createOrderFromCart(orderCreate, userId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Cart by user with id=" + userId + " not found");
    }

    @Test
    void createOrderFromCart_ShouldThrowException_WhenCartEmpty() {
        cart.getProducts().clear();
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        assertThatThrownBy(() -> orderService.createOrderFromCart(orderCreate, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Can not create order from empty cart");
    }

    @Test
    void cancelOrder_ShouldCancelOrder_WhenUserIsOwner() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderMapper.toDomain(any(OrderEntity.class))).thenAnswer(invocation -> buildOrderDto(invocation.getArgument(0)));

        Order result = orderService.cancelOrder(orderId, userId);

        assertThat(result.status()).isEqualTo(OrderStatus.CANCELED);
        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenUserIsNotOwner() {
        Long otherUserId = 99L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.cancelOrder(orderId, otherUserId))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessageContaining("You can`t cancel not your order");
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenOrderNotPaid() {
        order.setStatus(OrderStatus.DELIVERED);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.cancelOrder(orderId, userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Order must be in PAID status for this operation");
    }

    @Test
    void updateStatusToDelivered_ShouldChangeStatus_WhenOrderPaid() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderMapper.toDomain(any(OrderEntity.class))).thenAnswer(invocation -> buildOrderDto(invocation.getArgument(0)));

        Order result = orderService.updateStatusToDelivered(orderId);

        assertThat(result.status()).isEqualTo(OrderStatus.DELIVERED);
        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    void updateStatusToDelivered_ShouldThrowException_WhenOrderNotPaid() {
        order.setStatus(OrderStatus.DELIVERED);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThatThrownBy(() -> orderService.updateStatusToDelivered(orderId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Order must be in PAID status for this operation");
    }

    @Test
    void getOrderById_ShouldReturnOrder_WhenUserIsOwner() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDomain(order)).thenReturn(buildOrderDto(order));

        Order result = orderService.getOrderById(orderId, userId);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(orderId);
    }

    @Test
    void getOrderById_ShouldThrowAccessDenied_WhenUserIsNotOwnerAndNotAdmin() {
        Long otherUserId = 99L;
        UserEntity otherUser = new UserEntity();
        otherUser.setId(otherUserId);
        otherUser.setRole(UserRole.USER);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(userRepository.findById(otherUserId)).thenReturn(Optional.of(otherUser));

        assertThatThrownBy(() -> orderService.getOrderById(orderId, otherUserId))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessageContaining("You can only view your own orders");
    }
}