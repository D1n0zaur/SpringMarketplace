package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.User.User;
import com.marketplace.SelfPraktik.DTO.User.UserCreate;
import com.marketplace.SelfPraktik.Entities.UserEntity;
import com.marketplace.SelfPraktik.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(UserCreate request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("User with this email is already exist");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        UserEntity userEntity = new UserEntity(
                request.username(),
                request.email(),
                encodedPassword,
                request.birth()
        );

        UserEntity toSave = repository.save(userEntity);

        return new User(
                toSave.getId(),
                toSave.getName(),
                toSave.getEmail(),
                toSave.getBirth(),
                toSave.getRole()
        );
    }
}
