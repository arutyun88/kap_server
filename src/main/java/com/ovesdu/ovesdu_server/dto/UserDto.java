package com.ovesdu.ovesdu_server.dto;

import com.ovesdu.ovesdu_server.datasource.entities.RoleEntity;
import com.ovesdu.ovesdu_server.datasource.entities.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Builder
@Getter
public class UserDto {
    private Long id;
    private String username;
    private String phoneNumber;
    private String email;
    private String displayName;
    private Collection<String> roles;

    public static UserDto mapByEntity(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .displayName(entity.getDisplayName())
                .roles(entity.getRoles().stream().map(RoleEntity::getName).toList())
                .build();
    }
}
