package com.marketplace.SelfPraktik.Respositories;

import com.marketplace.SelfPraktik.Entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
