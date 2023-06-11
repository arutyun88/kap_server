package com.kap.kap_server.service;

import com.kap.kap_server.dto.TokensDto;
import com.kap.kap_server.dto.UserCreateDto;
import com.kap.kap_server.exceptions.AlreadyExistException;
import com.kap.kap_server.dto.*;
import com.kap.kap_server.exceptions.NotFoundException;

public interface UserService {
    String getDisplayName(
            String value,
            String deviceOs,
            String deviceId
    ) throws NotFoundException;

    TokensDto createUser(
            UserCreateDto userCreateDto
    ) throws AlreadyExistException;

    TokensDto authUser(String username, String password) throws NotFoundException;
}
