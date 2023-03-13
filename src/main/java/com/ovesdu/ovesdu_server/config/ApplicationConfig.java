package com.ovesdu.ovesdu_server.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.ovesdu.ovesdu_server.datasource.local.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Value("${app.secret-key}")
    private String SECRET_KEY;
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return userRepository::findByUsername;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(SECRET_KEY.getBytes());
    }
}
