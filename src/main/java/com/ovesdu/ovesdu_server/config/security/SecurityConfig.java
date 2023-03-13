package com.ovesdu.ovesdu_server.config.security;

import com.ovesdu.ovesdu_server.config.consts.Role;
import com.ovesdu.ovesdu_server.config.security.filters.DeviceHeaderFilter;
import com.ovesdu.ovesdu_server.config.security.filters.JwtAuthenticationFilter;
import com.ovesdu.ovesdu_server.config.security.filters.LocaleHeaderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ovesdu.ovesdu_server.config.consts.Paths.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final LocaleHeaderFilter localeHeaderFilter;
    private final DeviceHeaderFilter deviceHeaderFilter;
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(PATH_AUTH + "/**").permitAll()
                .requestMatchers(PATH_ADMIN + "/**")
                        .hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_OWNER.name())
                .requestMatchers(PATH_MANAGE + "/**")
                        .hasAnyAuthority(Role.ROLE_MANAGER.name(), Role.ROLE_OWNER.name())
                .requestMatchers(PATH_ROLES + "/**").hasAnyAuthority(Role.ROLE_OWNER.name())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(localeHeaderFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(deviceHeaderFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
