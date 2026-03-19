package com.marketplace.SelfPraktik.Repositories;

import com.marketplace.SelfPraktik.Entities.CategoryEntity;
import com.marketplace.SelfPraktik.Entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);

}
