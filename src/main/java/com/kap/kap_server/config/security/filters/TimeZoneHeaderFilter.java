package com.kap.kap_server.config.security.filters;

import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.kap.kap_server.config.consts.Headers.*;
import static com.kap.kap_server.config.consts.Headers.TIME_ZONE;
import static com.kap.kap_server.config.consts.Paths.*;

@Component
public class TimeZoneHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (!request.getServletPath().startsWith(PATH_AUTH + PATH_LOGIN) &&
                !request.getServletPath().startsWith(PATH_AUTH + PATH_REGISTRATION)
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        String locale = request.getHeader(APP_LOCALE);
        String timeZone = request.getHeader(TIME_ZONE);
        if (timeZone == null || timeZone.isEmpty()) {
            FilterHelper.error(response, LocalizedResponseMessageKey.TIME_ZONE_HEADER_REQUIRED, locale);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
