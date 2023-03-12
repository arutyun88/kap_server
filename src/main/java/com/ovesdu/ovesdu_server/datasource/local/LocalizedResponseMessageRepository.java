package com.ovesdu.ovesdu_server.datasource.local;

import com.ovesdu.ovesdu_server.datasource.entities.LocalizedResponseMessageEntity;
import com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizedResponseMessageRepository extends JpaRepository<LocalizedResponseMessageEntity, Long> {
    LocalizedResponseMessageEntity findByKeyAndLocale(LocalizedResponseMessageKey key, String locale);
}
