package com.kap.kap_server.service;

import com.kap.kap_server.dto.TokensDto;
import com.kap.kap_server.dto.UserCreateDto;
import com.kap.kap_server.exceptions.AlreadyExistException;
import com.kap.kap_server.dto.*;
import com.kap.kap_server.exceptions.NotFoundException;
import com.kap.kap_server.exceptions.UnauthorizedException;

public interface UserService {
    String getDisplayName(String value, DeviceDto deviceDto) throws NotFoundException;

    TokensDto signUp(UserCreateDto userCreateDto, String timeZone) throws AlreadyExistException;

    TokensDto signIn(UserSignInDto userSignInDto, String timeZone) throws UnauthorizedException, AlreadyExistException;

    void updateVisit(String username, boolean visitWithAlias) throws UnauthorizedException;
}
