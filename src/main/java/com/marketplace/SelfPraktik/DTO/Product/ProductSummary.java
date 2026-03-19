package com.marketplace.SelfPraktik.DTO.Product;

import java.math.BigDecimal;

public record ProductSummary(
        Long id,
        String name,
        BigDecimal price
) {}