package com.kap.kap_server.service;

import com.kap.kap_server.config.consts.Role;
import com.kap.kap_server.exceptions.RoleNotFoundException;
import com.kap.kap_server.exceptions.UserNotFoundException;

public interface RoleService {
    void addNewRoleFromUser(String username, Role role) throws RoleNotFoundException, UserNotFoundException;
}
