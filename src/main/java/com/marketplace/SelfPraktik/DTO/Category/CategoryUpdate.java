package com.marketplace.SelfPraktik.DTO.Category;

import jakarta.validation.constraints.*;

import java.util.Optional;

public class CategoryUpdate {
    private Optional<String> name = Optional.empty();

    private Optional<String> description = Optional.empty();

    // Геттеры и сеттеры
    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }
}