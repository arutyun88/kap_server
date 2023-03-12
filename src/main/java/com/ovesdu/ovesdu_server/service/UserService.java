package com.ovesdu.ovesdu_server.service;

import com.ovesdu.ovesdu_server.dto.*;
import com.ovesdu.ovesdu_server.exceptions.AlreadyExistException;
import com.ovesdu.ovesdu_server.exceptions.NotFoundException;

public interface UserService {
    String getDisplayName(
            String value,
            String deviceOs,
            String deviceId
    ) throws NotFoundException;

    TokensDto createUser(
            UserCreateDto userCreateDto
    ) throws AlreadyExistException;
}
