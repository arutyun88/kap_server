package com.kap.kap_server.datasource.local;

import com.kap.kap_server.datasource.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    DeviceEntity findByDeviceId(String deviceId);
}
