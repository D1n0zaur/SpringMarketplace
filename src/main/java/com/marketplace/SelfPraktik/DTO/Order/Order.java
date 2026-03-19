package com.marketplace.SelfPraktik.DTO.Order;

import com.marketplace.SelfPraktik.DTO.User.UserSummary;
import com.marketplace.SelfPraktik.DTO.Product.ProductSummary;
import com.marketplace.SelfPraktik.Entities.Enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record Order(
        Long id,
        String address,
        UserSummary user,
        List<ProductSummary> products,
        BigDecimal orderPrice,
        LocalDate date,
        OrderStatus status
) {}