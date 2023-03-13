package com.ovesdu.ovesdu_server.config.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey;
import com.ovesdu.ovesdu_server.config.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ovesdu.ovesdu_server.config.consts.Headers.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_TYPE)) {
            filterChain.doFilter(request, response);
            return;
        }
        String locale = request.getHeader(APP_LOCALE);
        try {
            final String token = authHeader.substring(AUTH_HEADER_TYPE.length());
            final String username = jwtService.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(token, userDetails) && jwtService.isAccessToken(token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    FilterHelper.forbidden(response, LocalizedResponseMessageKey.TOKEN_NOT_VALID, locale);
                }
            }
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            FilterHelper.forbidden(response, LocalizedResponseMessageKey.TOKEN_INVALID, locale);
        } catch (Exception e) {
            FilterHelper.forbidden(response, LocalizedResponseMessageKey.UNKNOWN_ERROR, locale);
        }

    }
}
