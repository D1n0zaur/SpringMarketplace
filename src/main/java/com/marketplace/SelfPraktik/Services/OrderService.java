package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.Order.Order;
import com.marketplace.SelfPraktik.DTO.Order.OrderCreate;
import com.marketplace.SelfPraktik.DTO.Order.OrderUpdate;
import com.marketplace.SelfPraktik.Entities.OrderEntity;
import com.marketplace.SelfPraktik.Mappers.OrderMapper;
import com.marketplace.SelfPraktik.Repositories.OrderRepository;

import com.marketplace.SelfPraktik.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final UserRepository userRepository;
    private final OrderMapper mapper;

    public List<Order> getAllOrders() {
        List<OrderEntity> allEntities = repository.findAll();

        return allEntities.stream()
                .map(mapper::toDomain).toList();
    }

    public List<Order> getAllOrdersByUser(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }

        List<OrderEntity> entities = repository.findByUserId(userId);

        return entities.stream()
                .map(mapper::toDomain).toList();
    }

    public Order getOrderById(Long id) {
        OrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        return mapper.toDomain(entity);
    }

    @Transactional
    public Order createOrder(OrderCreate request) {
        return null;
    }

    @Transactional
    public Order updateOrder(Long id, OrderUpdate request) {
        return null;
    }

    @Transactional
    public void deleteOrder(Long id) {}

}
