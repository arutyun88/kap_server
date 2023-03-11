package com.ovesdu.ovesdu_server.service.impl;

import com.ovesdu.ovesdu_server.datasource.entities.DeviceEntity;
import com.ovesdu.ovesdu_server.datasource.entities.UserEntity;
import com.ovesdu.ovesdu_server.datasource.entities.enums.DeviceOs;
import com.ovesdu.ovesdu_server.datasource.entities.enums.LocalizedResponseMessageKey;
import com.ovesdu.ovesdu_server.datasource.local.DeviceRepository;
import com.ovesdu.ovesdu_server.datasource.local.UserRepository;
import com.ovesdu.ovesdu_server.dto.TokensDto;
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
    final UserRepository userRepository;
    final DeviceRepository deviceRepository;

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
    public TokensDto createUser(UserEntity user, DeviceEntity device) throws AlreadyExistException {

        UserEntity fUserEntity = userRepository.findByUsername(user.getUsername());
        if (fUserEntity != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.USERNAME_ALREADY_EXIST.name());
        fUserEntity = userRepository.findByEmail(user.getEmail());
        if (fUserEntity != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.EMAIL_ALREADY_EXIST.name());
        fUserEntity = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (fUserEntity != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.PHONE_NUMBER_ALREADY_EXIST.name());

        DeviceEntity fDeviceEntity = deviceRepository.findByDeviceId(device.getDeviceId());
        if (fDeviceEntity != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.DEVICE_ALREADY_EXIST.name());

        final UserEntity entity = userRepository.save(user);
        device.setUser(entity);
        deviceRepository.save(device);

        return new TokensDto("access", "refresh");
    }
}
