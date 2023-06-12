package com.kap.kap_server.datasource.local;

import com.kap.kap_server.datasource.entities.TokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokensRepository extends JpaRepository<TokensEntity, Long> {
    TokensEntity findByDeviceId(Long deviceId);
}
