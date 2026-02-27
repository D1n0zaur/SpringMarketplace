package com.marketplace.SelfPraktik.DTO.User;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Optional;

public class UserUpdate {
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private Optional<String> username = Optional.empty();

    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email is too long")
    private Optional<String> email = Optional.empty();

    @Past(message = "Date of birth must be in the past")
    private Optional<LocalDate> birth = Optional.empty();

    // Геттеры и сеттеры
    public Optional<String> getUsername() {
        return username;
    }

    public void setUsername(Optional<String> username) {
        this.username = username;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }

    public Optional<LocalDate> getBirth() {
        return birth;
    }

    public void setBirth(Optional<LocalDate> birth) {
        this.birth = birth;
    }
}
