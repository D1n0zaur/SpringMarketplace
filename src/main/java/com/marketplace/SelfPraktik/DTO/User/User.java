package com.marketplace.SelfPraktik.DTO.User;

import com.marketplace.SelfPraktik.Entities.Models.UserRole;

import java.time.LocalDate;

public record User(
        Long id,
        String username,
        String email,
        LocalDate birth,
        UserRole role
) {}
