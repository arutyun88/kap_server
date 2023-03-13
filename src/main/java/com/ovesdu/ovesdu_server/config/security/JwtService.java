package com.ovesdu.ovesdu_server.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ovesdu.ovesdu_server.datasource.entities.TokensEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    private final Algorithm algorithm;
    private final String CLAIM_TOKEN_TYPE_KEY = "token_type";
    private final String CLAIM_TOKEN_TYPE_ACCESS = "access_token";

    public String extractUsername(String token) throws JWTVerificationException {
        return decode(token).getSubject();
    }

    public TokensEntity generateToken(UserDetails userDetails) {
        final long HOUR = 1000 * 60;
        final String accessToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + HOUR * 24))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim(
                        "roles",
                        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
                )
                .withClaim(CLAIM_TOKEN_TYPE_KEY, CLAIM_TOKEN_TYPE_ACCESS)
                .sign(algorithm);
        final String refreshToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim(CLAIM_TOKEN_TYPE_KEY, "refresh_token")
                .withExpiresAt(new Date(System.currentTimeMillis() + HOUR * 24 * 7))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        return TokensEntity.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAccessToken(String token) {
        return decode(token).getClaim(CLAIM_TOKEN_TYPE_KEY).asString().equals(CLAIM_TOKEN_TYPE_ACCESS);
    }

    private boolean isTokenExpired(String token) {
        return decode(token).getExpiresAt().before(new Date());
    }

    private DecodedJWT decode(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

}
