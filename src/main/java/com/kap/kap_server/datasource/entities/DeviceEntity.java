package com.kap.kap_server.datasource.entities;

import com.kap.kap_server.datasource.entities.enums.DeviceOs;
import com.kap.kap_server.datasource.entities.enums.DeviceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "_device")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Enumerated(EnumType.STRING)
    private DeviceOs deviceOs;

    private String deviceId;

    @ManyToOne(fetch = EAGER)
    @Setter
    private UserEntity user;

    public static boolean validate(
            String deviceId,
            String deviceType,
            String deviceOs
    ) {
        final DeviceType type = DeviceType.getByName(deviceType);
        return !deviceId.isEmpty() && DeviceOs.validate(deviceOs, type);
    }
}
