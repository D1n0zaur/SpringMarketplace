package com.marketplace.SelfPraktik.Mappers;

import com.marketplace.SelfPraktik.DTO.Cart.Cart;
import com.marketplace.SelfPraktik.DTO.Product.ProductSummary;
import com.marketplace.SelfPraktik.DTO.User.UserSummary;
import com.marketplace.SelfPraktik.Entities.CartEntity;
import com.marketplace.SelfPraktik.Entities.ProductEntity;
import com.marketplace.SelfPraktik.Entities.UserEntity;

import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    private UserSummary userToSummary(UserEntity entity) {
        return new UserSummary(entity.getId(), entity.getUsername());
    }
    private ProductSummary productToSummary(ProductEntity entity) {
        return new ProductSummary(entity.getId(), entity.getName(), entity.getPrice());
    }

    public Cart toDomain(CartEntity entity) {
        if(entity == null) {
            return null;
        }

        return new Cart(
                entity.getId(),
                userToSummary(entity.getUser()),
                entity.getProducts().stream()
                        .map(this::productToSummary).toList(),
                entity.getTotalPrice()
        );
    }
}
