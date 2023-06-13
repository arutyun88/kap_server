package com.kap.kap_server.dto;

public record UserSignInDto(
        String username,
        String password,
        boolean visitWithAlias,
        DeviceDto device
) {
}
