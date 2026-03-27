package com.marketplace.SelfPraktik.Mappers;

import com.marketplace.SelfPraktik.DTO.Order.Order;
import com.marketplace.SelfPraktik.DTO.Product.ProductSummary;
import com.marketplace.SelfPraktik.DTO.User.UserSummary;
import com.marketplace.SelfPraktik.Entities.OrderEntity;
import com.marketplace.SelfPraktik.Entities.ProductEntity;
import com.marketplace.SelfPraktik.Entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    private UserSummary userToSummary(UserEntity entity) {
        return new UserSummary(entity.getId(), entity.getUsername());
    }

    private ProductSummary productToSummary(ProductEntity entity) {
        return new ProductSummary(entity.getId(), entity.getName(), entity.getPrice());
    }

    public Order toDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getAddress(),
                userToSummary(entity.getUser()),
                entity.getOrderedProducts().stream().map(this::productToSummary).toList(),
                entity.getOrderPrice(),
                entity.getDate(),
                entity.getStatus()
        );
    }
}
