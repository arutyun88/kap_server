package com.ovesdu.ovesdu_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokensDto {
    private String accessToken;
    private String refreshToken;
}
