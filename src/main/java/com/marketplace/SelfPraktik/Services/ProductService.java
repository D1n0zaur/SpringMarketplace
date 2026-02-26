package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.Product.Product;
import com.marketplace.SelfPraktik.DTO.Product.ProductCreate;
import com.marketplace.SelfPraktik.DTO.Product.ProductUpdate;
import com.marketplace.SelfPraktik.Respositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final static Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return null;
    }

    public Product getProductById(Long id) {
        return null;
    }

    public List<Product> getProductsByCategory(String category) {
        return null;
    }

    public Product createProduct(ProductCreate request) {
        return null;
    }

    public Product updateProduct(Long id, ProductUpdate updateDto) {
        return null;
    }

    public Product deleteProduct(Long id) {
        return null;
    }
}
