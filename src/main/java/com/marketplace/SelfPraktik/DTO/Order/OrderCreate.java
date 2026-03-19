package com.marketplace.SelfPraktik.DTO.Order;

import jakarta.validation.constraints.*;

import java.util.List;

public record OrderCreate(
        @NotNull(message = "Address is required")
        @Size(min = 10, max = 300, message = "Address length must be between 10 and 300")
        String address,

        @NotNull(message = "User ID is required")
        @Positive(message = "User id must be positive")
        Long userId,

        @NotNull(message = "Products ID is required")
        @Size(min = 1, message = "Order must contain at least one product")
        List<Long> productsId
) {}
