package com.kap.kap_server.datasource.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "_role")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity roleEntity = (RoleEntity) o;
        return id.equals(roleEntity.id) && name.equals(roleEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
