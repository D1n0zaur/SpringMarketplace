package com.marketplace.SelfPraktik.DTO.User.Auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "Username or email is required")
        String email,

        @NotBlank(message = "Password is required")
        String password
) {}