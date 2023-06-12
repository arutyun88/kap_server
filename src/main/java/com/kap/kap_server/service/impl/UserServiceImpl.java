package com.kap.kap_server.service.impl;

import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import com.kap.kap_server.config.consts.Role;
import com.kap.kap_server.datasource.entities.*;
import com.kap.kap_server.datasource.local.*;
import com.kap.kap_server.dto.TokensDto;
import com.kap.kap_server.dto.UserCreateDto;
import com.kap.kap_server.exceptions.AlreadyExistException;
import com.kap.kap_server.exceptions.NotFoundException;
import com.kap.kap_server.exceptions.UnauthorizedException;
import com.kap.kap_server.service.DeviceService;
import com.kap.kap_server.service.TokensService;
import com.kap.kap_server.service.UserService;
import com.kap.kap_server.config.security.JwtService;
import com.kap.kap_server.datasource.entities.enums.DeviceOs;
import com.kap.kap_server.dto.*;
import com.kap.kap_server.service.VisitsService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final TokensRepository tokensRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final PasswordEncoder passwordEncoder;
    private final VisitsService visitsService;
    private final TokensService tokensService;
    private final DeviceService deviceService;
    private final JwtService jwtService;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        final UserEntity createdUser = userRepository.save(user);

        final DeviceEntity device = userCreateDto.getDevice().toEntity(createdUser);
        device.setUser(createdUser);
        final DeviceEntity createdDevice = deviceRepository.save(device);

        final TokensEntity tokens = jwtService.generateToken(createdUser);

        tokensRepository.save(
                new TokensEntity(
                        null,
                        tokens.getAccessToken(),
                        tokens.getRefreshToken(),
                        createdUser,
                        createdDevice
                )
        );

        return new TokensDto(tokens.getAccessToken(), tokens.getRefreshToken());
    }

    @Override
    public TokensDto signIn(UserSignInDto userSignInDto) throws UnauthorizedException, AlreadyExistException {
        final var fUserEntity = userRepository.findByUsername(userSignInDto.getUsername());
        if (fUserEntity == null) {
            throw new UnauthorizedException(LocalizedResponseMessageKey.INVALID_USERNAME_OR_PASSWORD.name());
        }
        final var mPassword = passwordEncoder.matches(userSignInDto.getPassword(), fUserEntity.getPassword());
        if (!mPassword) {
            throw new UnauthorizedException(LocalizedResponseMessageKey.INVALID_USERNAME_OR_PASSWORD.name());
        }

        final var uDeviceEntity = deviceService.updateDevice(userSignInDto.getDevice(), fUserEntity);
        final var uTokens = tokensService.updateTokens(uDeviceEntity, fUserEntity);
        visitsService.updateVisit(fUserEntity);

        return new TokensDto(uTokens.getAccessToken(), uTokens.getRefreshToken());
    }
}
