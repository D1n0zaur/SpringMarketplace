package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.Config.FileStorageConfig;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;
    private final FileStorageConfig fileStorageConfig;
    private final static Logger log = LoggerFactory.getLogger(ProductService.class);

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

    @Transactional
    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Product not fount");
        }

        repository.deleteById(id);
    }

    @Transactional
    public Product saveProductImage(Long productId, MultipartFile file) throws IOException {
        ProductEntity product = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFilename = UUID.randomUUID().toString() + extension;

        Path uploadPath = Paths.get(fileStorageConfig.getUploadDir());

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        if (product.getImageFilename() != null) {
            Path oldFilePath = uploadPath.resolve(product.getImageFilename());
            try {
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                log.warn("Failed to delete old image: {}", oldFilePath);
            }
        }

        product.setImageFilename(newFilename);
        repository.save(product);

        return mapper.toDomain(product);
    }

    public ProductEntity getProductEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }
}
