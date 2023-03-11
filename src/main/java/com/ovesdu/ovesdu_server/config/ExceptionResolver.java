package com.ovesdu.ovesdu_server.config;

import com.ovesdu.ovesdu_server.dto.ResponseWrapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@EnableWebMvc
@RestControllerAdvice
public class ExceptionResolver extends ResponseEntityExceptionHandler {
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
        if (ex.getClass().equals(NoHandlerFoundException.class)) {
            responseWrapper = new ResponseWrapper(null, "Resource not found");
        } else {
            responseWrapper = new ResponseWrapper(null, "Unknown error");
        }
        return ResponseEntity
                .status(statusCode)
                .headers(headers)
                .body(responseWrapper);
    }
}
