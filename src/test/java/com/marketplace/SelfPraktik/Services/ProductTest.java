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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    private CategoryEntity category;
    private ProductEntity product;
    private Product productDTO;
    private ProductCreate productCreate;
    private ProductUpdate productUpdate;

    @BeforeEach
    void setUp() {
        category = new CategoryEntity("Electronics", "Devices");
        category.setId(1L);

        product = new ProductEntity("IPhone", "Smartphone", BigDecimal.valueOf(999.99), category);
        product.setId(1L);

        productDTO = new Product(1L, "Electronics", "IPhone", "Smartphone", BigDecimal.valueOf(999.99), null);

        productCreate = new ProductCreate("IPhone", "Smartphone", BigDecimal.valueOf(999.99), 1L);

        productUpdate = new ProductUpdate();
        productUpdate.setName(Optional.of("Galaxy S22"));
        productUpdate.setPrice(Optional.of(BigDecimal.valueOf(799.99)));
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(mapper.toDomain(product)).thenReturn(productDTO);

        List<Product> result = service.getAllProducts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("IPhone");
        verify(productRepository).findAll();
        verify(mapper).toDomain(product);
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.toDomain(product)).thenReturn(productDTO);

        Product result = service.getProductById(1L);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        verify(productRepository).findById(1L);
        verify(mapper).toDomain(product);
    }

    @Test
    void getProductById_ShouldThrowException_WhenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getProductById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Product not found");
    }

    @Test
    void createProduct_ShouldReturnProduct_WhenCategoryExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);
        when(mapper.toDomain(any(ProductEntity.class))).thenReturn(productDTO);

        Product result = service.createProduct(productCreate);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("IPhone");
        verify(categoryRepository).findById(1L);
        verify(productRepository).save(any(ProductEntity.class));
        verify(mapper).toDomain(any(ProductEntity.class));
    }

    @Test
    void createProduct_ShouldThrowException_WhenCategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.createProduct(productCreate))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Category not found");
    }

    @Test
    void updateProduct_ShouldUpdateNameAndPrice_WhenPresented() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);
        when(mapper.toDomain(product)).thenReturn(productDTO);

        service.updateProduct(1L, productUpdate);

        assertThat(product.getName()).isEqualTo("Galaxy S22");
        assertThat(product.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(799.99));
        verify(productRepository).save(product);
    }

    @Test
    void updateProduct_ShouldThrowException_WhenPriceInvalid() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductUpdate invalidPriceUpdate = new ProductUpdate();
        invalidPriceUpdate.setPrice(Optional.of(BigDecimal.valueOf(-100)));

        assertThatThrownBy(() -> service.updateProduct(1L, invalidPriceUpdate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Price must be positive");
    }

    @Test
    void deleteProduct_ShouldDelete_WhenExists() {
        when(productRepository.existsById(1L)).thenReturn(true);
        // Не нужно мокать deleteById, т.к. он void, просто проверяем вызов.
        service.deleteProduct(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenNotFound() {
        when(productRepository.existsById(124L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteProduct(124L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Product not found");
    }
}