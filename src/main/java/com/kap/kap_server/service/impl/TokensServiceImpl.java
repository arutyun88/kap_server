package com.kap.kap_server.service.impl;

import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import com.kap.kap_server.config.security.JwtService;
import com.kap.kap_server.datasource.entities.DeviceEntity;
import com.kap.kap_server.datasource.entities.TokensEntity;
import com.kap.kap_server.datasource.entities.UserEntity;
import com.kap.kap_server.datasource.local.TokensRepository;
import com.kap.kap_server.exceptions.AlreadyExistException;
import com.kap.kap_server.service.TokensService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokensServiceImpl implements TokensService {
    private final TokensRepository tokensRepository;
    private final JwtService jwtService;

    @Override
    public TokensEntity updateTokens(DeviceEntity deviceEntity, UserEntity userEntity) throws AlreadyExistException {
        final var gTokens = jwtService.generateToken(userEntity);
        final var fTokens = tokensRepository.findByDeviceId(deviceEntity.getId());
        if (fTokens != null) {
            if (!fTokens.getUser().getId().equals(userEntity.getId())) {
                throw new AlreadyExistException(LocalizedResponseMessageKey.DEVICE_ALREADY_EXIST.name());
            }
            fTokens.setAccessToken(gTokens.getAccessToken());
            fTokens.setRefreshToken(gTokens.getRefreshToken());
            return tokensRepository.save(fTokens);
        }
        return tokensRepository.save(
                new TokensEntity(null, gTokens.getAccessToken(), gTokens.getRefreshToken(), userEntity, deviceEntity)
        );
    }
}
