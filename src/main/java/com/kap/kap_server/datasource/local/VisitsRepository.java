package com.kap.kap_server.datasource.local;

import com.kap.kap_server.datasource.entities.UserEntity;
import com.kap.kap_server.datasource.entities.VisitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitsRepository extends JpaRepository<VisitsEntity, Long> {
    VisitsEntity findByUserAndVisitedWithAlias(UserEntity user, boolean visitAsAlias);
}
