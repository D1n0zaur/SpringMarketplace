package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.Respositories.CategoryRepository;
import com.marketplace.SelfPraktik.Respositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryService {
    private final CategoryRepository repository;
    private final static Logger log = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }
}
