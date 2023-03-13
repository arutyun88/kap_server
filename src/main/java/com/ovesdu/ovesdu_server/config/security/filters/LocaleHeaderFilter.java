package com.ovesdu.ovesdu_server.config.security.filters;

import com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ovesdu.ovesdu_server.config.consts.Headers.APP_LOCALE;

@Component
public class LocaleHeaderFilter extends OncePerRequestFilter {
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
