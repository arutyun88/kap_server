package com.ovesdu.ovesdu_server.config.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
                FilterHelper.error(response, "device-type in the header is required");
            } else if (deviceId == null || deviceId.isEmpty()) {
                FilterHelper.error(response, "device-id in the header is required");
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);

        }

    }
}
