package com.ovesdu.ovesdu_server.config.security.filters;

import com.ovesdu.ovesdu_server.datasource.entities.enums.LocalizedResponseMessageKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
            FilterHelper.error(response, LocalizedResponseMessageKey.APP_LOCALE_HEADER_REQUIRED, "en");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
