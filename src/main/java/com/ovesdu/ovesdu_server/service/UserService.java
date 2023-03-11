package com.ovesdu.ovesdu_server.service;

import com.ovesdu.ovesdu_server.datasource.entities.DeviceEntity;
import com.ovesdu.ovesdu_server.datasource.entities.UserEntity;
import com.ovesdu.ovesdu_server.dto.TokensDto;
import com.ovesdu.ovesdu_server.exceptions.AlreadyExistException;
import com.ovesdu.ovesdu_server.exceptions.NotFoundException;

public interface UserService {
    String getDisplayName(
            String value,
            String deviceOs,
            String deviceId
    ) throws NotFoundException;

    TokensDto createUser(
            UserEntity user,
            DeviceEntity device
    ) throws AlreadyExistException;
}
