package com.kap.kap_server.dto;

import com.kap.kap_server.datasource.entities.RoleEntity;
import com.kap.kap_server.datasource.entities.UserEntity;
import com.kap.kap_server.datasource.entities.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateDto {
    private String username;
    private String alias;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String location;
    private String gender;
    private String password;
    private DeviceDto device;

    public UserEntity toEntity(RoleEntity role, TimeZone timeZone) {
        final List<RoleEntity> roles = new ArrayList<>();
        roles.add(role);
        return new UserEntity(
                null,
                username,
                alias,
                phoneNumber,
                email,
                firstName,
                lastName,
                location,
                timeZone,
                Gender.getByName(gender),
                new Date(),
                password,
                roles
        );
    }
}
