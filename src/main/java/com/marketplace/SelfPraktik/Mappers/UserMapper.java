package com.marketplace.SelfPraktik.Mappers;

import com.marketplace.SelfPraktik.DTO.User.User;

import com.marketplace.SelfPraktik.Entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getBirth(),
                userEntity.getRole()
        );
    }
}
