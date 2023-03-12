package com.ovesdu.ovesdu_server.config;

import com.ovesdu.ovesdu_server.dto.ResponseWrapper;
import com.ovesdu.ovesdu_server.service.LocalizedResponseMessageService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey.*;


@EnableWebMvc
@RestControllerAdvice
@AllArgsConstructor
public class ExceptionResolver extends ResponseEntityExceptionHandler {
    public final LocalizedResponseMessageService localizedResponseMessageService;

    public static final String APP_LOCALE = "app-locale";

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NotNull Exception ex,
            Object body,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode statusCode,
            @NotNull WebRequest request
    ) {
        final String locale = request.getHeader(APP_LOCALE);
        ResponseWrapper responseWrapper;
        final String message;
        if (ex.getClass().equals(NoHandlerFoundException.class)) {
            message = localizedResponseMessageService.find(RESOURCE_NOT_FOUND, locale);
        } else {
            message = localizedResponseMessageService.find(UNKNOWN_ERROR, locale);
        }
        responseWrapper = new ResponseWrapper(null, message);
        return ResponseEntity
                .status(statusCode)
                .headers(headers)
                .body(responseWrapper);
    }
}
