package com.marketplace.SelfPraktik.DTO.Product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductUpdate {
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private Optional<String> name = Optional.empty();

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private Optional<String> description = Optional.empty();

    @Positive(message = "Price must be positive")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @DecimalMax(value = "999999.99", message = "Price cannot exceed 999999.99")
    private Optional<BigDecimal> price = Optional.empty();

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be positive")
    private Optional<Long> categoryId = Optional.empty();



    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<BigDecimal> getPrice() {
        return price;
    }

    public void setPrice(Optional<BigDecimal> price) {
        this.price = price;
    }

    public Optional<Long> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Optional<Long> categoryId) {
        this.categoryId = categoryId;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }
}