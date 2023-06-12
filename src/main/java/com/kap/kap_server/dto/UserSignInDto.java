package com.kap.kap_server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignInDto {
    private String username;
    private String password;
    private DeviceDto device;
}
