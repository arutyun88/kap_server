package com.kap.kap_server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BadRequestException extends Exception {
    @Getter
    private final String message;
}