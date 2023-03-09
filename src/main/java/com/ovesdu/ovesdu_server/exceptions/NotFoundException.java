package com.ovesdu.ovesdu_server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NotFoundException extends Exception {
    @Getter
    private final String message;
}
