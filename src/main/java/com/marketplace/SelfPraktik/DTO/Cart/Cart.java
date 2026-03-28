package com.marketplace.SelfPraktik.DTO.Cart;

import com.marketplace.SelfPraktik.DTO.User.UserSummary;
import com.marketplace.SelfPraktik.DTO.Product.ProductSummary;
import com.marketplace.SelfPraktik.Entities.Enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record Cart(
        Long id,
        UserSummary user,
        List<ProductSummary> products,
        BigDecimal totalPrice
) {}