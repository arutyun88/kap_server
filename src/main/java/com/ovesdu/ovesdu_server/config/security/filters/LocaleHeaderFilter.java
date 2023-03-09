package com.ovesdu.ovesdu_server.config.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class LocaleHeaderFilter extends OncePerRequestFilter {
    public static final String APP_LOCALE = "app-locale";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String localeHeader = request.getHeader(APP_LOCALE);
        if (localeHeader == null || localeHeader.isEmpty()) {
            _error(response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    void _error(HttpServletResponse response) throws IOException {
        response.setStatus(BAD_REQUEST.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", "app-locale in the header is required");
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
