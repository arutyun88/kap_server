package com.ovesdu.ovesdu_server.datasource.local;

import com.ovesdu.ovesdu_server.datasource.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    DeviceEntity findByDeviceId(String deviceId);
}
