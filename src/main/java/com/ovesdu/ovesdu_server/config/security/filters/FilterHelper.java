package com.ovesdu.ovesdu_server.config.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ovesdu.ovesdu_server.config.AppResponse;
import com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey;
import com.ovesdu.ovesdu_server.dto.ResponseWrapper;
import com.ovesdu.ovesdu_server.exceptions.BadRequestException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class FilterHelper {
    static void error(HttpServletResponse response, LocalizedResponseMessageKey key, String locale) throws IOException {
        ResponseEntity<ResponseWrapper> resource = AppResponse.error(new BadRequestException(key.name()), locale);
        response.setStatus(resource.getStatusCode().value());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), resource.getBody());
    }
}
