package com.marketplace.SelfPraktik.Repositories;

import com.marketplace.SelfPraktik.Entities.Enums.OrderStatus;
import com.marketplace.SelfPraktik.Entities.OrderEntity;
import com.marketplace.SelfPraktik.Entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setHashedPassword("dummy");
        user.setBirth(LocalDate.of(1990, 1, 1));
        user.setRole(com.marketplace.SelfPraktik.Entities.Enums.UserRole.USER);
        user = userRepository.save(user);
    }

    @Test
    void findByUserId_ShouldReturnOrdersForUser() {
        OrderEntity order1 = new OrderEntity();
        order1.setUser(user);
        order1.setOrderPrice(BigDecimal.valueOf(200));
        order1.setDate(LocalDate.now());
        order1.setStatus(OrderStatus.PAID);
        order1.setAddress("Address 1");
        orderRepository.save(order1);

        OrderEntity order2 = new OrderEntity();
        order2.setUser(user);
        order2.setOrderPrice(BigDecimal.valueOf(300));
        order2.setDate(LocalDate.now());
        order2.setStatus(OrderStatus.DELIVERED);
        order2.setAddress("Address 2");
        orderRepository.save(order2);

        List<OrderEntity> orders = orderRepository.findByUserId(user.getId());

        assertThat(orders).hasSize(2);
        assertThat(orders).extracting(OrderEntity::getOrderPrice).containsExactlyInAnyOrder(BigDecimal.valueOf(200), BigDecimal.valueOf(300));
    }

    @Test
    void findByUserId_ShouldReturnEmptyList_WhenUserHasNoOrders() {
        List<OrderEntity> orders = orderRepository.findByUserId(user.getId());
        assertThat(orders).isEmpty();
    }
}