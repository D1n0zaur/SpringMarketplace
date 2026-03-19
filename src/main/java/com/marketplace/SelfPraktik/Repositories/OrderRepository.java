package com.marketplace.SelfPraktik.Repositories;

import com.marketplace.SelfPraktik.Entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {}
