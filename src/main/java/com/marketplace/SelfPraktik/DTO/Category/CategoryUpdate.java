package com.marketplace.SelfPraktik.DTO.Category;

import jakarta.validation.constraints.*;

import java.util.Optional;

public class CategoryUpdate {
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private Optional<String> name = Optional.empty();
    @Size(max = 500, message = "Description cannot exceed 500 characters")
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