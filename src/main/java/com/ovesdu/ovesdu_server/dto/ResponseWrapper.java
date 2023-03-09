package com.ovesdu.ovesdu_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWrapper {
    private Object data;
    private String message;
}
