package com.ovesdu.ovesdu_server.datasource.local;

import com.ovesdu.ovesdu_server.datasource.entities.TokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokensRepository extends JpaRepository<TokensEntity, Long> {
}
