package com.ovesdu.ovesdu_server.service;

import com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey;

public interface LocalizedResponseMessageService {
    String find(LocalizedResponseMessageKey key, String locale);
}
