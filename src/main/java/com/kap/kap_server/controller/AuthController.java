package com.kap.kap_server.controller;

import com.kap.kap_server.config.AppResponse;
import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import com.kap.kap_server.dto.ResponseWrapper;
import com.kap.kap_server.dto.TokensDto;
import com.kap.kap_server.dto.UserCreateDto;
import com.kap.kap_server.service.UserService;
import com.kap.kap_server.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.kap.kap_server.config.consts.Headers.*;
import static com.kap.kap_server.config.consts.Paths.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    final UserService userService;

    @GetMapping(PATH_AUTH_INFO + "/{usernameOrEmailOrPhoneNumber}")
    public ResponseEntity<ResponseWrapper> getDisplayName(
            @RequestHeader(APP_LOCALE) String locale,
            @PathVariable String usernameOrEmailOrPhoneNumber,
            @RequestBody() DeviceDto deviceDto
    ) {
        try {
            String displayName = userService.getDisplayName(usernameOrEmailOrPhoneNumber, deviceDto);
            return AppResponse.ok(displayName, locale);
        } catch (Exception exception) {
            return AppResponse.error(exception, locale);
        }
    }

    @PostMapping(PATH_AUTH_REGISTRATION)
    public ResponseEntity<ResponseWrapper> signUp(
            @RequestHeader(APP_LOCALE) String locale,
            @RequestHeader(TIME_ZONE) String timeZone,
            @RequestBody() UserCreateDto userCreateDto
    ) {
        try {
            TokensDto tokens = userService.signUp(userCreateDto, timeZone);
            return AppResponse.created(tokens, locale, LocalizedResponseMessageKey.USER_REGISTERED);
        } catch (Exception exception) {
            return AppResponse.error(exception, locale);
        }
    }

    @PostMapping(PATH_AUTH_LOGIN)
    public ResponseEntity<ResponseWrapper> signIn(
            @RequestHeader(APP_LOCALE) String locale,
            @RequestHeader(TIME_ZONE) String timeZone,
            @RequestBody() UserSignInDto userSignInDto
    ) {
        try {
            TokensDto tokens = userService.signIn(userSignInDto, timeZone);
            return AppResponse.ok(tokens, locale, LocalizedResponseMessageKey.USER_AUTHORIZED);
        } catch (Exception exception) {
            return AppResponse.error(exception, locale);
        }
    }
}
