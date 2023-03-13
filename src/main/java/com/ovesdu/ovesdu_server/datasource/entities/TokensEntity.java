package com.ovesdu.ovesdu_server.datasource.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "_tokens")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokensEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String accessToken;
    private String refreshToken;

    @ManyToOne(fetch = EAGER)
    private UserEntity user;

    @ManyToOne(fetch = EAGER)
    private DeviceEntity device;
}
