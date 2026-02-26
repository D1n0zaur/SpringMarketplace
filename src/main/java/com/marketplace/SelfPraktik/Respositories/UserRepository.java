package com.marketplace.SelfPraktik.Respositories;

import com.marketplace.SelfPraktik.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
