package com.kap.kap_server;

import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import com.kap.kap_server.config.consts.Role;
import com.kap.kap_server.datasource.entities.LocalizedResponseMessageEntity;
import com.kap.kap_server.datasource.entities.RoleEntity;
import com.kap.kap_server.datasource.local.LocalizedResponseMessageRepository;
import com.kap.kap_server.datasource.local.RoleRepository;
import com.kap.kap_server.datasource.entities.UserEntity;
import com.kap.kap_server.datasource.local.UserRepository;
import com.kap.kap_server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
public class KapServerApplication {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public KapServerApplication(RoleRepository roleRepository,
                                UserService userService,
                                UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(KapServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(
            LocalizedResponseMessageRepository localizedResponseMessageRepository
    ) {
        return args -> {

            for (Role role : Role.values()) {
                var fRole = roleRepository.findByName(role.name());
                if (fRole == null) {
                    roleRepository.save(new RoleEntity(null, role.name()));
                }
            }

            var role = roleRepository.findByName(Role.ROLE_OWNER.name());
            var userEntity = new UserEntity(
                    null,
                    "arutyun",
                    "+79202024422",
                    "gevorkyan.arutyun@gmail.com",
                    "Arutyun Gevorkyan",
                    "password",
                    List.of(role)
            );
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            var sUser = userRepository.save(userEntity);
            log.info("DATE BY INSTANT: {}", sUser.getCreatedAt());
            log.info("DATE BY DATE: {}", Date.from(sUser.getCreatedAt()));
            log.info("DATE BY LOCAL +3: {}", LocalDateTime.ofInstant(sUser.getCreatedAt(), ZoneId.of("GMT+3")));
            log.info("ACCESS_TOKEN: {}", userService.authUser(userEntity.getUsername(), userEntity.getPassword()).getAccessToken());

            localizedResponseMessageRepository.save(
                    new LocalizedResponseMessageEntity(null, "ru", LocalizedResponseMessageKey.USER_NOT_FOUND, "Пользователь не найден")
            );
            localizedResponseMessageRepository.save(
                    new LocalizedResponseMessageEntity(null, "ru", LocalizedResponseMessageKey.SUCCESS, "Успешно")
            );
            localizedResponseMessageRepository.save(
                    new LocalizedResponseMessageEntity(null, "en", LocalizedResponseMessageKey.SUCCESS, "Success")
            );
            localizedResponseMessageRepository.save(
                    new LocalizedResponseMessageEntity(null, "ru", LocalizedResponseMessageKey.RESOURCE_NOT_FOUND, "Ресурс не найден")
            );
            localizedResponseMessageRepository.save(
                    new LocalizedResponseMessageEntity(null, "en", LocalizedResponseMessageKey.USER_REGISTERED, "The user is registered")
            );
            localizedResponseMessageRepository.save(
                    new LocalizedResponseMessageEntity(null, "ru", LocalizedResponseMessageKey.USER_REGISTERED, "Пользователь зарегистрирован")
            );

        };
    }

}
