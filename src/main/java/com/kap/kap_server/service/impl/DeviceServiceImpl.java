package com.kap.kap_server.service.impl;

import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import com.kap.kap_server.datasource.entities.DeviceEntity;
import com.kap.kap_server.datasource.entities.UserEntity;
import com.kap.kap_server.datasource.local.DeviceRepository;
import com.kap.kap_server.dto.DeviceDto;
import com.kap.kap_server.exceptions.AlreadyExistException;
import com.kap.kap_server.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Override
    public DeviceEntity updateDevice(DeviceDto deviceDto, UserEntity userEntity) throws AlreadyExistException {
        final var fDeviceEntity = deviceRepository.findByDeviceId(deviceDto.getDeviceId());
        if (fDeviceEntity == null) {
            return deviceRepository.save(deviceDto.toEntity(userEntity));
        } else {
            if (!fDeviceEntity.getUser().getId().equals(userEntity.getId())) {
                throw new AlreadyExistException(LocalizedResponseMessageKey.DEVICE_ALREADY_EXIST.name());
            }
        }
        return fDeviceEntity;
    }
}
