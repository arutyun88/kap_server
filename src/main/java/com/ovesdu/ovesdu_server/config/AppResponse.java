package com.ovesdu.ovesdu_server.config;

import com.ovesdu.ovesdu_server.dto.ResponseWrapper;
import com.ovesdu.ovesdu_server.exceptions.BadRequestException;
import com.ovesdu.ovesdu_server.exceptions.ForbiddenException;
import com.ovesdu.ovesdu_server.exceptions.NotFoundException;
import com.ovesdu.ovesdu_server.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AppResponse {

    public static ResponseEntity<ResponseWrapper> ok(Object data, String locale) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper(data, "Success")
        );
    }

    public static ResponseEntity<ResponseWrapper> created(Object data, String locale) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseWrapper(data, "Created")
        );
    }

    public static ResponseEntity<ResponseWrapper> error(Exception exception, String locale) {
        if (exception.getClass().equals(BadRequestException.class)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseWrapper(null, exception.getMessage())
            );
        } else if (exception.getClass().equals(UnauthorizedException.class)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseWrapper(null, exception.getMessage())
            );
        } else if (exception.getClass().equals(ForbiddenException.class)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ResponseWrapper(null, exception.getMessage())
            );
        } else if (exception.getClass().equals(NotFoundException.class)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseWrapper(null, exception.getMessage())
            );
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseWrapper(null, exception.getMessage())
            );
        }
    }
}

