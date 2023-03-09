package com.ovesdu.ovesdu_server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UnauthorizedException extends Exception {
    @Getter
    private final String message;
}