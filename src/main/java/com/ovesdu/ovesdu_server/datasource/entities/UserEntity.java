package com.ovesdu.ovesdu_server.datasource.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String username;
    private String phoneNumber;
    private String email;
    private String displayName;
    private String password;
    @ManyToMany(fetch = EAGER)
    private Collection<RoleEntity> roles = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(id, userEntity.id) &&
                Objects.equals(username, userEntity.username) &&
                Objects.equals(phoneNumber, userEntity.phoneNumber) &&
                Objects.equals(email, userEntity.email) &&
                Objects.equals(displayName, userEntity.displayName) &&
                Objects.equals(password, userEntity.password) &&
                Objects.equals(roles, userEntity.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, phoneNumber, email, displayName, password, roles);
    }

}
