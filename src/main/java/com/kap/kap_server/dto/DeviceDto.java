package com.kap.kap_server.dto;

import com.kap.kap_server.datasource.entities.UserEntity;
import com.kap.kap_server.datasource.entities.enums.DeviceOs;
import com.kap.kap_server.datasource.entities.enums.DeviceType;
import com.kap.kap_server.datasource.entities.DeviceEntity;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    private String deviceType;
    private String deviceOs;
    private String deviceId;

    public DeviceEntity toEntity(UserEntity user) {
        return new DeviceEntity(
                null,
                DeviceType.getByName(deviceType),
                DeviceOs.getByName(deviceOs),
                deviceId,
                user
        );
    }
}
