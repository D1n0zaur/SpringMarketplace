package com.marketplace.SelfPraktik.DTO.User.Auth;

public record AuthResponse(
        String token,
        String type,
        Long userId,
        String username,
        String role
) {}