package com.ovesdu.ovesdu_server.service;

import com.ovesdu.ovesdu_server.datasource.entities.enums.LocalizedResponseMessageKey;

public interface LocalizedResponseMessageService {
    String find(LocalizedResponseMessageKey key, String locale);
}
