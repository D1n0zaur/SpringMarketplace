package com.marketplace.SelfPraktik.Repositories;

import com.marketplace.SelfPraktik.Entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByCategoryId(Long categoryId);
}
