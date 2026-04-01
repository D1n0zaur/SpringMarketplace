package com.marketplace.SelfPraktik.Controllers;

import com.marketplace.SelfPraktik.Config.FileStorageConfig;
import com.marketplace.SelfPraktik.DTO.Product.Product;
import com.marketplace.SelfPraktik.DTO.Product.ProductUpdate;
import com.marketplace.SelfPraktik.DTO.Product.ProductCreate;
import com.marketplace.SelfPraktik.Entities.ProductEntity;
import com.marketplace.SelfPraktik.Services.ProductService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final static Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final FileStorageConfig fileStorageConfig;

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
    public ResponseEntity<List<Product>> getProductsByCategoryName(@PathVariable String categoryName) {
        log.info("Called getProductsByCategoryName with name: {}", categoryName);
        return ResponseEntity.ok(productService.getProductsByCategory(categoryName));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreate request) {
        log.info("Called createProduct with categoryId: {}", request.categoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdate updateDto
    ) {
        log.info("Called updateProduct with id: {}", id);
        Product updated = productService.updateProduct(id, updateDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Called deleteProduct with id: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> uploadProductImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        log.info("Uploading image for product id: {}", id);

        return ResponseEntity.ok(productService.saveProductImage(id, file));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getProductImage(@PathVariable Long id) throws IOException {
        ProductEntity product = productService.getProductEntityById(id); // нужно добавить этот метод в сервис
        if (product.getImageFilename() == null) {
            return ResponseEntity.notFound().build();
        }

        Path imagePath = Paths.get(fileStorageConfig.getUploadDir()).resolve(product.getImageFilename());
        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(imagePath.toUri());

        String contentType = Files.probeContentType(imagePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}