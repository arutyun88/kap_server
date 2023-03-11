package com.ovesdu.ovesdu_server.datasource.entities;

import com.ovesdu.ovesdu_server.datasource.entities.enums.DeviceOs;
import com.ovesdu.ovesdu_server.datasource.entities.enums.DeviceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "_device")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private DeviceType deviceType;
    @Enumerated(EnumType.ORDINAL)
    private DeviceOs deviceOs;
    private String deviceId;

    public static boolean validate(
            String deviceId,
            String deviceType,
            String deviceOs
    ) {
        return !deviceId.isEmpty() && DeviceType.contains(deviceType) && DeviceOs.contains(deviceOs);
    }
}