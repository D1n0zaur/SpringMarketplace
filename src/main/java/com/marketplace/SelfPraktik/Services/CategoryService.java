package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.Category.Category;
import com.marketplace.SelfPraktik.DTO.Category.CategoryCreate;
import com.marketplace.SelfPraktik.DTO.Category.CategoryUpdate;
import com.marketplace.SelfPraktik.Entities.CategoryEntity;
import com.marketplace.SelfPraktik.Mappers.CategoryMapper;
import com.marketplace.SelfPraktik.Repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<Category> getAllCategories() {
        List<CategoryEntity> allEntities = repository.findAll();

        return allEntities.stream()
                .map(mapper::toDomain).toList();
    }

    public Category getCategoryById(Long id) {
        CategoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        return mapper.toDomain(entity);
    }

    @Transactional
    public Category createCategory(CategoryCreate request) {
        if (repository.existsByName(request.name())) {
            throw new IllegalArgumentException("Category with this name is already exists");
        }

        CategoryEntity entity = new CategoryEntity(
                request.name(),
                request.description()
        );
        repository.save(entity);

        return mapper.toDomain(entity);
    }

    @Transactional
    public Category updateCategory(Long id, CategoryUpdate request) {
        CategoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        request.getName().ifPresent(newName -> {
            if (newName.length() < 2 || newName.length() > 100) {
                throw new IllegalArgumentException("Category name must be between 2 and 100 characters");
            }
            if (!newName.equals(entity.getName()) && repository.existsByName(newName)) {
                throw new IllegalArgumentException("Category name must be unique");
            }

            entity.setName(newName);
        });
        request.getDescription().ifPresent(newDescription -> {
            if (newDescription.length() > 500) {
                throw new IllegalArgumentException("Description cannot exceed 500 characters");
            }

            entity.setDescription(newDescription);
        });

        CategoryEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Transactional
    public void deleteCategory(Long id) {
        CategoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        repository.deleteById(id);
    }
}
