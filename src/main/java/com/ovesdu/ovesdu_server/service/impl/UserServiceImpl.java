package com.ovesdu.ovesdu_server.service.impl;

import com.ovesdu.ovesdu_server.datasource.entities.DeviceEntity;
import com.ovesdu.ovesdu_server.datasource.entities.RoleEntity;
import com.ovesdu.ovesdu_server.datasource.entities.TokensEntity;
import com.ovesdu.ovesdu_server.datasource.entities.UserEntity;
import com.ovesdu.ovesdu_server.datasource.entities.enums.DeviceOs;
import com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey;
import com.ovesdu.ovesdu_server.config.consts.Role;
import com.ovesdu.ovesdu_server.datasource.local.DeviceRepository;
import com.ovesdu.ovesdu_server.datasource.local.RoleRepository;
import com.ovesdu.ovesdu_server.datasource.local.TokensRepository;
import com.ovesdu.ovesdu_server.datasource.local.UserRepository;
import com.ovesdu.ovesdu_server.dto.*;
import com.ovesdu.ovesdu_server.exceptions.AlreadyExistException;
import com.ovesdu.ovesdu_server.exceptions.NotFoundException;
import com.ovesdu.ovesdu_server.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final TokensRepository tokensRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public String getDisplayName(
            String value,
            String deviceOs,
            String deviceId
    ) throws NotFoundException {
        final UserEntity user;
        if (value.startsWith("+")) {
            user = userRepository.findByPhoneNumber(value);
        } else if (value.contains("@")) {
            user = userRepository.findByEmail(value);
        } else {
            user = userRepository.findByUsername(value);
        }
        if (user != null) {
            if (DeviceOs.getByName(deviceOs) != DeviceOs.WEB) {
                final DeviceEntity device = deviceRepository.findByDeviceId(deviceId);
                return device != null && device.getUser() == user ? user.getDisplayName() : value;
            } else {
                return value;
            }
        } else {
            throw new NotFoundException(LocalizedResponseMessageKey.USER_NOT_FOUND.name());
        }
    }

    @Override
    public TokensDto createUser(
            UserCreateDto userCreateDto
    ) throws AlreadyExistException {

        UserEntity fUserEntity = userRepository.findByUsername(userCreateDto.getUser().getUsername());
        if (fUserEntity != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.USERNAME_ALREADY_EXIST.name());
        fUserEntity = userRepository.findByEmail(userCreateDto.getUser().getEmail());
        if (fUserEntity != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.EMAIL_ALREADY_EXIST.name());
        fUserEntity = userRepository.findByPhoneNumber(userCreateDto.getUser().getPhoneNumber());
        if (fUserEntity != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.PHONE_NUMBER_ALREADY_EXIST.name());

        DeviceEntity fDeviceEntity = deviceRepository.findByDeviceId(userCreateDto.getDevice().getDeviceId());
        if (fDeviceEntity != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.DEVICE_ALREADY_EXIST.name());

        final RoleEntity roleUser = roleRepository.findByName(Role.ROLE_USER.name());
        final UserEntity user = userCreateDto.getUser().toEntity(roleUser);
        final UserEntity createdUser = userRepository.save(user);

        final DeviceEntity device = userCreateDto.getDevice().toEntity(createdUser);
        device.setUser(createdUser);
        final DeviceEntity createdDevice = deviceRepository.save(device);

        final TokensEntity createdTokens = tokensRepository.save(
                new TokensEntity(
                        null,
                        "created_access_token",
                        "created_refresh_token",
                        createdUser,
                        createdDevice
                )
        );

        return new TokensDto(createdTokens.getAccessToken(), createdTokens.getRefreshToken());
    }
}
