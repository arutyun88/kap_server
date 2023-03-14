package com.ovesdu.ovesdu_server.service.impl;

import com.ovesdu.ovesdu_server.config.consts.LocalizedResponseMessageKey;
import com.ovesdu.ovesdu_server.config.consts.Role;
import com.ovesdu.ovesdu_server.datasource.local.RoleRepository;
import com.ovesdu.ovesdu_server.datasource.local.UserRepository;
import com.ovesdu.ovesdu_server.exceptions.RoleNotFoundException;
import com.ovesdu.ovesdu_server.exceptions.UserNotFoundException;
import com.ovesdu.ovesdu_server.service.RoleService;
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
