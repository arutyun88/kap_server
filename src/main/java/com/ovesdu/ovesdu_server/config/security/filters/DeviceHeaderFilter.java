package com.ovesdu.ovesdu_server.config.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class DeviceHeaderFilter extends OncePerRequestFilter {
    public static final String DEVICE_TYPE = "device-type";
    public static final String DEVICE_ID = "device-id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String deviceType = request.getHeader(DEVICE_TYPE);
        String deviceId = request.getHeader(DEVICE_ID);
        if (request.getServletPath().startsWith("/api/auth/info")) {
            if (deviceType == null || deviceType.isEmpty()) {
                _error(response, "device-type in the header is required");
            } else if (deviceId == null || deviceId.isEmpty()) {
                _error(response, "device-id in the header is required");
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);

        }

    }

    void _error(HttpServletResponse response, String message) throws IOException {
        response.setStatus(BAD_REQUEST.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", message);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}