package com.marketplace.SelfPraktik.DTO.User;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserCreate(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be in format example@gmail.com")
        @Size(max = 100, message = "Email is too long")
        String email,

        @Past(message = "Date of birth must be in the past")
        @NotNull
        LocalDate birth,

        @NotBlank(message = "Password can`t be null")
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
        String password
) {}
