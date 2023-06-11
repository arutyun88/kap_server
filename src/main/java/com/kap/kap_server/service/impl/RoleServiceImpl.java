package com.kap.kap_server.service.impl;

import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import com.kap.kap_server.config.consts.Role;
import com.kap.kap_server.datasource.local.RoleRepository;
import com.kap.kap_server.exceptions.RoleNotFoundException;
import com.kap.kap_server.exceptions.UserNotFoundException;
import com.kap.kap_server.service.RoleService;
import com.kap.kap_server.datasource.local.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void addNewRoleFromUser(String username, Role role) throws RoleNotFoundException, UserNotFoundException {
        var fRole = roleRepository.findByName(role.name());
        if (fRole == null) {
            throw new RoleNotFoundException(LocalizedResponseMessageKey.ROLE_NOT_FOUND.name());
        }
        var fUser = userRepository.findByUsername(username);
        if (fUser == null) {
            throw new UserNotFoundException(LocalizedResponseMessageKey.USER_NOT_FOUND.name());
        }
        if (!fUser.getRoles().contains(fRole)) {
            fUser.getRoles().add(fRole);
            fUser.setUpdatedAt(Instant.now());
        }
        userRepository.save(fUser);
    }
}
