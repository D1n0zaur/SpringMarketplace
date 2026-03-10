package com.marketplace.SelfPraktik.DTO.User;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Optional;

public class UserUpdate {
    private Optional<String> username = Optional.empty();

    private Optional<String> email = Optional.empty();

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
