package com.marketplace.SelfPraktik.Mappers;

import com.marketplace.SelfPraktik.DTO.Product.Product;
import com.marketplace.SelfPraktik.Entities.ProductEntity;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toDomain(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getCategory().getName(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice()
        );
    }
}
