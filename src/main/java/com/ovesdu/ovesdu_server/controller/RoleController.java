package com.ovesdu.ovesdu_server.controller;

import com.ovesdu.ovesdu_server.config.AppResponse;
import com.ovesdu.ovesdu_server.config.consts.Role;
import com.ovesdu.ovesdu_server.dto.ResponseWrapper;
import com.ovesdu.ovesdu_server.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ovesdu.ovesdu_server.config.consts.Headers.APP_LOCALE;
import static com.ovesdu.ovesdu_server.config.consts.Paths.PATH_ROLES;

@RestController
@RequestMapping(PATH_ROLES)
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PutMapping("/user")
    public ResponseEntity<ResponseWrapper> addNewRole(
            @RequestHeader(APP_LOCALE) String locale,
            @RequestParam String username,
            @RequestParam String role
    ) {
        try {
            roleService.addNewRoleFromUser(username, Role.valueOf(role));
            return AppResponse.ok(null, locale);
        } catch (Exception exception) {
            return AppResponse.error(exception, locale);
        }
    }
}