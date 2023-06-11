package com.kap.kap_server.service;

import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;

public interface LocalizedResponseMessageService {
    String find(LocalizedResponseMessageKey key, String locale);
}
