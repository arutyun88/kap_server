package com.kap.kap_server.service;

import com.kap.kap_server.datasource.entities.DeviceEntity;
import com.kap.kap_server.datasource.entities.UserEntity;
import com.kap.kap_server.dto.DeviceDto;
import com.kap.kap_server.exceptions.AlreadyExistException;

public interface DeviceService {
    DeviceEntity updateDevice(DeviceDto deviceDto, UserEntity userEntity) throws AlreadyExistException;
}
