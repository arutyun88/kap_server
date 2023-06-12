package com.kap.kap_server.datasource.entities;

import jakarta.persistence.*;
import lombok.*;

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
    @Setter
    private String accessToken;
    @Setter
    private String refreshToken;

    @ManyToOne(fetch = EAGER)
    private UserEntity user;

    @OneToOne(fetch = EAGER)
    private DeviceEntity device;
}
