package com.ovesdu.ovesdu_server.service;

import com.ovesdu.ovesdu_server.exceptions.NotFoundException;

public interface UserService {
    String getDisplayName(String value) throws NotFoundException;
}
