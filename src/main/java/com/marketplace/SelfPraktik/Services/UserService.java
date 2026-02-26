package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.Respositories.ProductRepository;
import com.marketplace.SelfPraktik.Respositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
