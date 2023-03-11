package com.ovesdu.ovesdu_server.datasource.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Date created;
    @UpdateTimestamp
    private Date updated;
}
