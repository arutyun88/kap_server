package com.ovesdu.ovesdu_server.config.security.filters;

import com.ovesdu.ovesdu_server.datasource.entities.DeviceEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey.*;

@Slf4j
public class DeviceHeaderFilter extends OncePerRequestFilter {
    public static final String DEVICE_TYPE = "device-type";
    public static final String DEVICE_ID = "device-id";
    public static final String DEVICE_OS = "device-os";
    public static final String APP_LOCALE = "app-locale";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String deviceType = request.getHeader(DEVICE_TYPE);
        String deviceId = request.getHeader(DEVICE_ID);
        String deviceOs = request.getHeader(DEVICE_OS);
        String appLocale = request.getHeader(APP_LOCALE);
        if (request.getServletPath().startsWith("/api/auth/info")) {
            if (deviceType == null || deviceType.isEmpty()) {
                FilterHelper.error(response, DEVICE_TYPE_HEADER_REQUIRED, appLocale);
            } else if (deviceId == null || deviceId.isEmpty()) {
                FilterHelper.error(response, DEVICE_ID_HEADER_REQUIRED, appLocale);
            } else if (deviceOs == null || deviceOs.isEmpty()) {
                FilterHelper.error(response, DEVICE_OS_HEADER_REQUIRED, appLocale);
            } else if (!DeviceEntity.validate(deviceId, deviceType, deviceOs)) {
                FilterHelper.error(response, DEVICE_INFORMATION_IS_NOT_VALID, appLocale);
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);

        }

    }
}
