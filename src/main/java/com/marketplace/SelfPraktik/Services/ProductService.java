package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.Product.Product;
import com.marketplace.SelfPraktik.DTO.Product.ProductCreate;
import com.marketplace.SelfPraktik.DTO.Product.ProductUpdate;
import com.marketplace.SelfPraktik.Entities.CategoryEntity;
import com.marketplace.SelfPraktik.Entities.ProductEntity;
import com.marketplace.SelfPraktik.Mappers.ProductMapper;
import com.marketplace.SelfPraktik.Repositories.CategoryRepository;
import com.marketplace.SelfPraktik.Repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public List<Product> getAllProducts() {
        List<ProductEntity> allEntities = repository.findAll();

        return allEntities.stream()
                .map(mapper::toDomain).toList();
    }

    public Product getProductById(Long id) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return mapper.toDomain(entity);
    }

    public List<Product> getProductsByCategory(String categoryName) {
        CategoryEntity category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        List<ProductEntity> allEntities = repository.findAllByCategoryId(category.getId());

        return allEntities.stream()
                .map(mapper::toDomain).toList();
    }

    @Transactional
    public Product createProduct(ProductCreate request) {
        CategoryEntity category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        ProductEntity entity = new ProductEntity(
                request.name(),
                request.description(),
                request.price(),
                category
        );

        repository.save(entity);

        return mapper.toDomain(entity);
    }

    @Transactional
    public Product updateProduct(Long id, ProductUpdate updateDto) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        updateDto.getName().ifPresent(name -> {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Product name cannot be empty");
            }
            if (name.length() < 3 || name.length() > 100) {
                throw new IllegalArgumentException("Product name must be between 3 and 100 characters");
            }
            entity.setName(name);
        });

        updateDto.getDescription().ifPresent(description -> {
            if (description != null && description.length() > 1000) {
                throw new IllegalArgumentException("Description cannot exceed 1000 characters");
            }
            entity.setDescription(description);
        });

        updateDto.getPrice().ifPresent(price -> {
            if (price == null) {
                throw new IllegalArgumentException("Price cannot be null");
            }
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Price must be positive");
            }
            if (price.compareTo(new BigDecimal("0.01")) < 0) {
                throw new IllegalArgumentException("Price must be at least 0.01");
            }
            if (price.compareTo(new BigDecimal("999999.99")) > 0) {
                throw new IllegalArgumentException("Price cannot exceed 999999.99");
            }
            entity.setPrice(price);
        });

        updateDto.getCategoryId().ifPresent(newCategoryId -> {
            if (newCategoryId == null || newCategoryId <= 0) {
                throw new IllegalArgumentException("Category ID must be a positive number");
            }
            CategoryEntity category = categoryRepository.findById(newCategoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            entity.setCategory(category);
        });

        ProductEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Product not fount");
        }

        repository.deleteById(id);
    }
}
