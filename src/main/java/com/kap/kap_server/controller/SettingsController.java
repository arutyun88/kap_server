package com.kap.kap_server.controller;

import com.kap.kap_server.config.AppResponse;
import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import com.kap.kap_server.config.security.JwtService;
import com.kap.kap_server.dto.ResponseWrapper;
import com.kap.kap_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.kap.kap_server.config.consts.Headers.*;
import static com.kap.kap_server.config.consts.Paths.*;
import static com.kap.kap_server.config.consts.RequestParams.*;

@RestController
@RequestMapping(PATH_SETTINGS)
@RequiredArgsConstructor
public class SettingsController {
    final UserService userService;
    final JwtService jwtService;

    @PostMapping("/visit")
    public ResponseEntity<ResponseWrapper> visit(
            @RequestHeader(APP_LOCALE) String locale,
            @RequestHeader(AUTHORIZATION) String token,
            @RequestParam(ALIAS) boolean visitWithAlias
    ) {
        try {
            final String username = jwtService.extractUsername(token.substring(AUTH_HEADER_TYPE.length()));
            userService.updateVisit(username, visitWithAlias);
            return AppResponse.ok(null, locale, LocalizedResponseMessageKey.SUCCESS);
        } catch (Exception exception) {
            return AppResponse.error(exception, locale);
        }
    }
}
