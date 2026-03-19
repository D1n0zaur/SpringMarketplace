package com.marketplace.SelfPraktik.Controllers;

import com.marketplace.SelfPraktik.DTO.Product.Product;
import com.marketplace.SelfPraktik.DTO.Product.ProductUpdate;
import com.marketplace.SelfPraktik.DTO.Product.ProductCreate;
import com.marketplace.SelfPraktik.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final static Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Called getAllProducts");

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Called getProductById with id: {}", id);
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategoryName(
            @PathVariable String categoryName
    ) {
        log.info("Called getProductsByCategoryName with name: {}", categoryName);
        return ResponseEntity.ok(productService.getProductsByCategory(categoryName));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreate request) {
        log.info("Called createProduct with categoryId: {}", request.categoryId());

        Product created = productService.createProduct(request);
        URI location = URI.create("/products/" + created.id());

        return ResponseEntity
                .created(location)
                .body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdate updateDto
    ) {
        log.info("Called updateProduct with id: {}", id);

        Product updated = productService.updateProduct(id, updateDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Called deleteProduct with id: {}", id);

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}