package com.ovesdu.ovesdu_server.service.impl;

import com.ovesdu.ovesdu_server.datasource.entities.UserEntity;
import com.ovesdu.ovesdu_server.datasource.local.UserRepository;
import com.ovesdu.ovesdu_server.exceptions.NotFoundException;
import com.ovesdu.ovesdu_server.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    @Override
    public String getDisplayName(String value) throws NotFoundException {
        final UserEntity user;
        if (value.startsWith("+")) {
            user = userRepository.findByPhoneNumber(value);
        } else if (value.contains("@")) {
            user = userRepository.findByEmail(value);
        } else {
            user = userRepository.findByUsername(value);
        }
        // TODO return mapped UserInfoDTO
        if (user != null) {
            return user.getDisplayName();
        } else {
            throw new NotFoundException("User not found");
        }
    }
}
