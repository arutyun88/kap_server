package com.kap.kap_server.config.security.filters;

import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import com.kap.kap_server.config.consts.Paths;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.kap.kap_server.config.consts.Headers.*;

@Component
public class LocaleHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (!request.getServletPath().startsWith(Paths.PATH_AUTH)) {
            filterChain.doFilter(request, response);
            return;
        }
        String appLocale = request.getHeader(APP_LOCALE);
        String timeZone = request.getHeader(TIME_ZONE);

        if (appLocale == null || appLocale.isEmpty()) {
            FilterHelper.error(response, LocalizedResponseMessageKey.APP_LOCALE_HEADER_REQUIRED, "en");
        } else if (timeZone == null || timeZone.isEmpty()) {
            FilterHelper.error(response, LocalizedResponseMessageKey.TIME_ZONE_HEADER_REQUIRED, "en");
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
