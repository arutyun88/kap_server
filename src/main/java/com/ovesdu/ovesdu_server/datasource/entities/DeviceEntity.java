package com.ovesdu.ovesdu_server.datasource.entities;

import com.ovesdu.ovesdu_server.datasource.entities.enums.DeviceOs;
import com.ovesdu.ovesdu_server.datasource.entities.enums.DeviceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "_device")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Enumerated(EnumType.STRING)
    private DeviceOs deviceOs;

    private String deviceId;

    @ManyToOne(fetch = EAGER)
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
