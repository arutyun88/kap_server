package com.kap.kap_server.service;

import com.kap.kap_server.datasource.entities.DeviceEntity;
import com.kap.kap_server.datasource.entities.TokensEntity;
import com.kap.kap_server.datasource.entities.UserEntity;
import com.kap.kap_server.exceptions.AlreadyExistException;

public interface TokensService {
    TokensEntity updateTokens(DeviceEntity deviceEntity, UserEntity userEntity) throws AlreadyExistException;
}
