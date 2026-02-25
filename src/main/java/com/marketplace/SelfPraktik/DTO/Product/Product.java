package com.marketplace.SelfPraktik.DTO.Product;

public record Product(
    Long id,
    String category,
    String name,
    String description,
    double cost
) {}
