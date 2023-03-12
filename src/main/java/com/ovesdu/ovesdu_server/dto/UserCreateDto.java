package com.ovesdu.ovesdu_server.dto;

import com.ovesdu.ovesdu_server.datasource.entities.DeviceEntity;
import com.ovesdu.ovesdu_server.datasource.entities.RoleEntity;
import com.ovesdu.ovesdu_server.datasource.entities.UserEntity;
import com.ovesdu.ovesdu_server.datasource.entities.enums.DeviceOs;
import com.ovesdu.ovesdu_server.datasource.entities.enums.DeviceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateDto {
    private UserDto user;
    private DeviceDto device;


    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserDto {
        private String username;
        private String phoneNumber;
        private String email;
        private String displayName;
        private String password;

        public UserEntity toEntity(RoleEntity role) {
            final List<RoleEntity> roles = new ArrayList<>();
            roles.add(role);
            return new UserEntity(
                    null,
                    username,
                    phoneNumber,
                    email,
                    displayName,
                    password,
                    roles
            );
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class DeviceDto {
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
}

