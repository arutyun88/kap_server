package com.ovesdu.ovesdu_server.controller;

import com.ovesdu.ovesdu_server.config.AppResponse;
import com.ovesdu.ovesdu_server.config.security.filters.DeviceHeaderFilter;
import com.ovesdu.ovesdu_server.config.security.filters.LocaleHeaderFilter;
import com.ovesdu.ovesdu_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    final UserService userService;

    @GetMapping("/info/{usernameOrEmailOrPhoneNumber}")
    public ResponseEntity<?> getDisplayName(
            @RequestHeader(LocaleHeaderFilter.APP_LOCALE) String locale,
            @RequestHeader(DeviceHeaderFilter.DEVICE_TYPE) String deviceType,
            @RequestHeader(DeviceHeaderFilter.DEVICE_ID) String deviceId,
            @PathVariable String usernameOrEmailOrPhoneNumber
    ) {
        String displayName = userService.getDisplayName(usernameOrEmailOrPhoneNumber);
        return AppResponse.ok(displayName);
    }
}
