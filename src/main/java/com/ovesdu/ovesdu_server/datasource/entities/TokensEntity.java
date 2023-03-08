package com.ovesdu.ovesdu_server.datasource.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "_tokens")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokensEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String accessToken;
    private String refreshToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokensEntity tokensEntity = (TokensEntity) o;
        return Objects.equals(id, tokensEntity.id) &&
                Objects.equals(accessToken, tokensEntity.accessToken) &&
                Objects.equals(refreshToken, tokensEntity.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accessToken, refreshToken);
    }
}
