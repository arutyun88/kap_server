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
import com.kap.kap_server.datasource.entities.enums.DeviceOs;
import com.kap.kap_server.dto.*;
import com.kap.kap_server.service.VisitsService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.TimeZone;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final PasswordEncoder passwordEncoder;
    private final VisitsService visitsService;
    private final TokensService tokensService;
    private final DeviceService deviceService;

    @Override
    public String getDisplayName(String value, DeviceDto deviceDto) throws NotFoundException {
        final UserEntity user;
        if (value.startsWith("+")) {
            user = userRepository.findByPhoneNumber(value);
        } else if (value.contains("@")) {
            user = userRepository.findByEmail(value);
        } else {
            user = userRepository.findByUsername(value);
        }
        if (user != null) {
            if (DeviceOs.getByName(deviceDto.getDeviceOs()) != DeviceOs.WEB) {
                final DeviceEntity device = deviceRepository.findByDeviceId(deviceDto.getDeviceId());
                return device != null && device.getUser() == user ? user.getFirstName() : value;
            } else {
                return value;
            }
        } else {
            throw new NotFoundException(LocalizedResponseMessageKey.USER_NOT_FOUND.name());
        }
    }

    @Override
    public TokensDto signUp(UserCreateDto userCreateDto, String timeZone) throws AlreadyExistException {
        checkUser(userCreateDto);
        checkDevice(userCreateDto.getDevice().getDeviceId());
        final UserEntity user = userCreateDto.toEntity(
                roleRepository.findByName(Role.ROLE_USER.name()),
                TimeZone.getTimeZone(timeZone)
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        final UserEntity cUser = userRepository.save(user);
        final var uDevice = deviceService.updateDevice(userCreateDto.getDevice(), cUser);
        final var uTokens = tokensService.updateTokens(uDevice, cUser);
        return new TokensDto(uTokens.getAccessToken(), uTokens.getRefreshToken());
    }

    @Override
    public TokensDto signIn(
            UserSignInDto userSignInDto, String timeZone
    ) throws UnauthorizedException, AlreadyExistException {
        final var fUserEntity = findByUsernameAndPassword(userSignInDto.username(), userSignInDto.password());
        final var uDeviceEntity = deviceService.updateDevice(userSignInDto.device(), fUserEntity);
        final var uTokens = tokensService.updateTokens(uDeviceEntity, fUserEntity);
        updateTimeZone(fUserEntity, timeZone);
        return new TokensDto(uTokens.getAccessToken(), uTokens.getRefreshToken());
    }

    @Override
    public void updateVisit(String username, boolean visitWithAlias) throws UnauthorizedException {
        visitsService.updateVisit(findByUsername(username), visitWithAlias);
    }

    private void checkUser(UserCreateDto user) throws AlreadyExistException {
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.USERNAME_ALREADY_EXIST.name());
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.EMAIL_ALREADY_EXIST.name());
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()) != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.PHONE_NUMBER_ALREADY_EXIST.name());
    }

    private void checkDevice(String deviceId) throws AlreadyExistException {
        if (deviceService.checkDevice(deviceId) != null)
            throw new AlreadyExistException(LocalizedResponseMessageKey.DEVICE_ALREADY_EXIST.name());
    }

    private UserEntity findByUsername(String username) throws UnauthorizedException {
        final var fUserEntity = userRepository.findByUsername(username);
        if (fUserEntity == null) {
            throw new UnauthorizedException(LocalizedResponseMessageKey.INVALID_USERNAME_OR_PASSWORD.name());
        }
        return fUserEntity;
    }

    private UserEntity findByUsernameAndPassword(String username, String password) throws UnauthorizedException {
        final var fUserEntity = findByUsername(username);
        final var mPassword = passwordEncoder.matches(password, fUserEntity.getPassword());
        if (!mPassword) {
            throw new UnauthorizedException(LocalizedResponseMessageKey.INVALID_USERNAME_OR_PASSWORD.name());
        }
        return fUserEntity;
    }

    private void updateTimeZone(UserEntity user, String timeZone) {
        if (!user.getTimeZone().getID().equals(timeZone)) {
            user.setTimeZone(TimeZone.getTimeZone(timeZone));
            userRepository.save(user);
        }
    }
}
