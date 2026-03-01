package com.marketplace.SelfPraktik.Services;

import com.marketplace.SelfPraktik.DTO.User.User;
import com.marketplace.SelfPraktik.DTO.User.UserUpdate;
import com.marketplace.SelfPraktik.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    public List<User> getAllUsers() {
        return null;
    }

    public User getUserById(Long Id) {
        return null;
    }

    public User updateUser(Long id, UserUpdate userUpdateDTO) {
        return null;
    }

    public Void deleteUser(Long id) {
        return null;
    }
}
