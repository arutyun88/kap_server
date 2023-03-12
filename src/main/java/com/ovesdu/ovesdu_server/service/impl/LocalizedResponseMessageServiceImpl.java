package com.ovesdu.ovesdu_server.service.impl;

import com.ovesdu.ovesdu_server.datasource.entities.LocalizedResponseMessageEntity;
import com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey;
import com.ovesdu.ovesdu_server.datasource.local.LocalizedResponseMessageRepository;
import com.ovesdu.ovesdu_server.service.LocalizedResponseMessageService;
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
