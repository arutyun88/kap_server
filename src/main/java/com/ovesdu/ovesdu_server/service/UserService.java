package com.ovesdu.ovesdu_server.service;

import com.ovesdu.ovesdu_server.datasource.entities.UserEntity;
import com.ovesdu.ovesdu_server.dto.UserDto;
import com.ovesdu.ovesdu_server.exceptions.AlreadyExistException;
import com.ovesdu.ovesdu_server.exceptions.NotFoundException;

public interface UserService {
    String getDisplayName(
            String value,
            String deviceOs,
            String deviceId
    ) throws NotFoundException;

    UserDto createUser(
            UserEntity user
    ) throws AlreadyExistException;
}
