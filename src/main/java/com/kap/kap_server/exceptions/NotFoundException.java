package com.kap.kap_server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NotFoundException extends Exception {
    @Getter
    private final String message;
}
