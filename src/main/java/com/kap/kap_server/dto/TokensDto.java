package com.kap.kap_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokensDto {
    private String accessToken;
    private String refreshToken;
}
