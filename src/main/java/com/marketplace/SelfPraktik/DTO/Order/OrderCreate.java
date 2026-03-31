package com.marketplace.SelfPraktik.DTO.Order;

import jakarta.validation.constraints.*;

public record OrderCreate(
        @NotBlank(message = "Address is required")
        @Size(min = 10, max = 300, message = "Address length must be between 10 and 300")
        String address
) {}
