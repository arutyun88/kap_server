package com.ovesdu.ovesdu_server.config;

import com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey;
import com.ovesdu.ovesdu_server.dto.ResponseWrapper;
import com.ovesdu.ovesdu_server.exceptions.*;
import com.ovesdu.ovesdu_server.service.LocalizedResponseMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey.*;

public abstract class AppResponse {
    final static LocalizedResponseMessageService localizedResponseMessageService =
            StaticContextAccessor.getBean(LocalizedResponseMessageService.class);

    public static ResponseEntity<ResponseWrapper> ok(
            Object data,
            String locale
    ) {
        final String message = localizedResponseMessageService.find(SUCCESS, locale);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(data, message));
    }

    public static ResponseEntity<ResponseWrapper> created(
            Object data,
            String locale
    ) {
        final String message = localizedResponseMessageService.find(CREATED, locale);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper(data, message));
    }

    public static ResponseEntity<ResponseWrapper> created(
            Object data,
            String locale,
            LocalizedResponseMessageKey messageKey
    ) {
        final String message = localizedResponseMessageService.find(messageKey, locale);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper(data, message));
    }

    public static ResponseEntity<ResponseWrapper> error(
            Exception exception,
            String locale
    ) {
        final String message = localizedResponseMessageService.find(
                LocalizedResponseMessageKey.valueOf(exception.getMessage()), locale);

        if (exception.getClass().equals(BadRequestException.class)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper(null, message));
        } else if (exception.getClass().equals(UnauthorizedException.class)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseWrapper(null, message));
        } else if (exception.getClass().equals(ForbiddenException.class)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseWrapper(null, message));
        } else if (exception.getClass().equals(NotFoundException.class) ||
                exception.getClass().equals(UserNotFoundException.class)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper(null, message));
        } else if (exception.getClass().equals(AlreadyExistException.class)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseWrapper(null, message));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper(null, message));
        }
    }
}

