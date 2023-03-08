package com.ovesdu.ovesdu_server.datasource.local;

import com.ovesdu.ovesdu_server.datasource.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    UserEntity findByPhoneNumber(String phoneNumber);

    UserEntity findByEmail(String email);
}
