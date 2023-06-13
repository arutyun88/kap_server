package com.kap.kap_server.datasource.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "_visits")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VisitsEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Setter
    private Instant lastVisit;
    @Setter
    private boolean visitedWithAlias;

    @OneToOne(fetch = EAGER)
    private UserEntity user;
}
