package com.ovesdu.ovesdu_server.controller;

import com.ovesdu.ovesdu_server.config.AppResponse;
import com.ovesdu.ovesdu_server.config.security.filters.DeviceHeaderFilter;
import com.ovesdu.ovesdu_server.config.security.filters.LocaleHeaderFilter;
import com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey;
import com.ovesdu.ovesdu_server.dto.*;
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
    public ResponseEntity<ResponseWrapper> getDisplayName(
            @RequestHeader(LocaleHeaderFilter.APP_LOCALE) String locale,
            @RequestHeader(DeviceHeaderFilter.DEVICE_ID) String deviceId,
            @RequestHeader(DeviceHeaderFilter.DEVICE_OS) String deviceOs,
            @PathVariable String usernameOrEmailOrPhoneNumber
    ) {
        try {
            String displayName = userService.getDisplayName(
                    usernameOrEmailOrPhoneNumber,
                    deviceOs,
                    deviceId
            );
            return AppResponse.ok(displayName, locale);
        } catch (Exception exception) {
            return AppResponse.error(exception, locale);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<ResponseWrapper> createUser(
            @RequestHeader(LocaleHeaderFilter.APP_LOCALE) String locale,
            @RequestBody() UserCreateDto userCreateDto
    ) {
        try {
            TokensDto tokens = userService.createUser(userCreateDto);
            return AppResponse.created(tokens, locale, LocalizedResponseMessageKey.USER_REGISTERED);
        } catch (Exception exception) {
            return AppResponse.error(exception, locale);
        }
    }
}
