package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.Order.Order;
import com.marketplace.SelfPraktik.DTO.Order.OrderCreate;
import com.marketplace.SelfPraktik.DTO.Order.OrderUpdate;
import com.marketplace.SelfPraktik.Mappers.OrderMapper;
import com.marketplace.SelfPraktik.Repositories.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    public List<Order> getAllOrders() {
        return null;
    }

    public List<Order> getAllOrdersByUser(Long userId) {
        return null;
    }

    public Order getOrderById(Long id) {
        return null;
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
