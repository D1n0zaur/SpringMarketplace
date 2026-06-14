package com.marketplace.SelfPraktik.Repositories;

import com.marketplace.SelfPraktik.Entities.CategoryEntity;
import com.marketplace.SelfPraktik.Entities.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findAllByCategoryId_ShouldReturnProductsFromThatCategory() {
        CategoryEntity category = new CategoryEntity("Electronics", "Devices");
        CategoryEntity savedCategory = categoryRepository.save(category);

        ProductEntity product1 = new ProductEntity("Phone", "Smartphone", BigDecimal.valueOf(500), savedCategory);
        ProductEntity product2 = new ProductEntity("Laptop", "Computer", BigDecimal.valueOf(1000), savedCategory);
        productRepository.save(product1);
        productRepository.save(product2);

        List<ProductEntity> found = productRepository.findAllByCategoryId(savedCategory.getId());

        assertThat(found).hasSize(2);
        assertThat(found).extracting(ProductEntity::getName).containsExactlyInAnyOrder("Phone", "Laptop");
    }

    @Test
    void findAllByCategoryId_ShouldReturnEmptyList_WhenNoProducts() {
        CategoryEntity category = new CategoryEntity("Empty", "No products");
        CategoryEntity savedCategory = categoryRepository.save(category);

        List<ProductEntity> found = productRepository.findAllByCategoryId(savedCategory.getId());

        assertThat(found).isEmpty();
    }
}