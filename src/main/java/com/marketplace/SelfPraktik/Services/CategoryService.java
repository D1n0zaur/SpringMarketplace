package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.Repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final static Logger log = LoggerFactory.getLogger(CategoryService.class);
}
