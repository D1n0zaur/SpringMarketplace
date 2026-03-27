package com.marketplace.SelfPraktik.Mappers;

import com.marketplace.SelfPraktik.DTO.Category.Category;
import com.marketplace.SelfPraktik.Entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toDomain(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}
