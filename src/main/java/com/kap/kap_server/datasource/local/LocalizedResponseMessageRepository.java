package com.kap.kap_server.datasource.local;

import com.kap.kap_server.datasource.entities.LocalizedResponseMessageEntity;
import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizedResponseMessageRepository extends JpaRepository<LocalizedResponseMessageEntity, Long> {
    LocalizedResponseMessageEntity findByKeyAndLocale(LocalizedResponseMessageKey key, String locale);
}
