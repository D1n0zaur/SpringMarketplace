package com.marketplace.SelfPraktik.DTO.Product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductUpdate {
    private Optional<String> name = Optional.empty();

    private Optional<String> description = Optional.empty();

    private Optional<BigDecimal> price = Optional.empty();

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