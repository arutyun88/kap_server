package com.kap.kap_server.service.impl;

import com.kap.kap_server.datasource.entities.LocalizedResponseMessageEntity;
import com.kap.kap_server.datasource.local.LocalizedResponseMessageRepository;
import com.kap.kap_server.service.LocalizedResponseMessageService;
import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocalizedResponseMessageServiceImpl implements LocalizedResponseMessageService {

    final LocalizedResponseMessageRepository localizedResponseMessageRepository;

    @Override
    public String find(LocalizedResponseMessageKey key, String locale) {
        final LocalizedResponseMessageEntity entity = localizedResponseMessageRepository.findByKeyAndLocale(key, locale);
        if (entity != null) {
            return entity.getText();
        } else {
            return key.defaultValue;
        }
    }
}
