package com.kap.kap_server.service.impl;

import com.kap.kap_server.datasource.entities.UserEntity;
import com.kap.kap_server.datasource.entities.VisitsEntity;
import com.kap.kap_server.datasource.local.VisitsRepository;
import com.kap.kap_server.service.VisitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class VisitsServiceImpl implements VisitsService {
    private final VisitsRepository visitsRepository;

    @Override
    public void updateVisit(UserEntity user, boolean visitWithAlias) {
        final var currentTime = Instant.now();
        final var fLastVisit = visitsRepository.findByUserAndVisitedWithAlias(user, visitWithAlias);
        if (fLastVisit == null) {
            visitsRepository.save(new VisitsEntity(null, currentTime, visitWithAlias, user));
        } else {
            fLastVisit.setLastVisit(currentTime);
            visitsRepository.save(fLastVisit);
        }
    }
}
