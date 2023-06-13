package com.kap.kap_server.service;

import com.kap.kap_server.datasource.entities.UserEntity;

public interface VisitsService {
    void updateVisit(UserEntity user, boolean visitWithAlias);
}
