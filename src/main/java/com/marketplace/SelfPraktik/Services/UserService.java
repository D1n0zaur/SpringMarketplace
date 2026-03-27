package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.User.User;
import com.marketplace.SelfPraktik.DTO.User.UserUpdate;
import com.marketplace.SelfPraktik.Entities.UserEntity;
import com.marketplace.SelfPraktik.Mappers.UserMapper;
import com.marketplace.SelfPraktik.Repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public List<User> getAllUsers() {
        List<UserEntity> allEntities = repository.findAll();

        return allEntities.stream()
                .map(mapper::toDomain).toList();
    }

    public User getUserById(Long id) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return mapper.toDomain(entity);
    }

    public User getUserByEmail(String email) {
        UserEntity entity = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapper.toDomain(entity);
    }

    @Transactional
    public User updateUser(Long id, UserUpdate userUpdateDTO) {
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userUpdateDTO.getUsername().ifPresent(username -> {
            if (username.length() < 3 || username.length() > 50) {
                throw new IllegalArgumentException("Username must be between 3 and 50 characters");
            }

            entity.setUsername(username);
        });
        userUpdateDTO.getEmail().ifPresent(newEmail -> {
            if (!newEmail.equals(entity.getEmail()) && repository.existsByEmail(newEmail)) {
                throw new IllegalArgumentException("Email " + newEmail + " is already taken");
            }
            if (newEmail.length() > 100) {
                throw new IllegalArgumentException("Email is too long");
            }
            if (!newEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new IllegalArgumentException("Invalid email format");
            }

            entity.setEmail(newEmail);
        });
        userUpdateDTO.getBirth().ifPresent(birth -> {
            if (birth.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Birth date must be in the past");
            }
            entity.setBirth(birth);
        });

        UserEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }

        repository.deleteById(id);
    }
}
