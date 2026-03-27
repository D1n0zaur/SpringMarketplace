package com.marketplace.SelfPraktik.Controllers;

import com.marketplace.SelfPraktik.DTO.Category.Category;
import com.marketplace.SelfPraktik.DTO.Category.CategoryCreate;
import com.marketplace.SelfPraktik.DTO.Category.CategoryUpdate;
import com.marketplace.SelfPraktik.Services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final static Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Called method getAllCategories");

        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
        log.info("Called method getCategoryById with id: {}", id);

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryCreate request) {
        log.info("Called method createCategory");

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable("id") Long id,
            @RequestBody CategoryUpdate request
    ) {
        log.info("Called method updateCategory with id: {}", id);

        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        log.info("Called method deleteCategory with id: {}", id);
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}