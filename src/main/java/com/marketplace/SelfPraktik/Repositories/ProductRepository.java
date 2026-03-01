package com.marketplace.SelfPraktik.Repositories;

import com.marketplace.SelfPraktik.Entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
