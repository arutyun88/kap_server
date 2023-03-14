package com.ovesdu.ovesdu_server.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RoleNotFoundException extends Exception {
    @Getter
    private final String message;
}
