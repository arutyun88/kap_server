package com.kap.kap_server.datasource.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    @Setter
    private Instant updatedAt;
    @Setter
    private Instant deletedAt;
}
