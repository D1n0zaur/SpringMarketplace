package com.marketplace.SelfPraktik.DTO.Product;

import java.math.BigDecimal;

public record Product(
    Long id,
    String category,
    String name,
    String description,
    BigDecimal price
) {}
