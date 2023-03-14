package com.ovesdu.ovesdu_server.service;

import com.ovesdu.ovesdu_server.config.consts.Role;
import com.ovesdu.ovesdu_server.exceptions.RoleNotFoundException;
import com.ovesdu.ovesdu_server.exceptions.UserNotFoundException;

public interface RoleService {
    void addNewRoleFromUser(String username, Role role) throws RoleNotFoundException, UserNotFoundException;
}
