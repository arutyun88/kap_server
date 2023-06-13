package com.kap.kap_server;

import com.kap.kap_server.config.consts.LocalizedResponseMessageKey;
import com.kap.kap_server.config.consts.Role;
import com.kap.kap_server.datasource.entities.LocalizedResponseMessageEntity;
import com.kap.kap_server.datasource.entities.RoleEntity;
import com.kap.kap_server.datasource.entities.enums.Gender;
import com.kap.kap_server.datasource.local.LocalizedResponseMessageRepository;
import com.kap.kap_server.datasource.local.RoleRepository;
import com.kap.kap_server.datasource.entities.UserEntity;
import com.kap.kap_server.datasource.local.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
public class KapServerApplication {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public KapServerApplication(RoleRepository roleRepository,
                                UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
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

            userRepository.save(new UserEntity(
                    null,
                    "arutyun",
                    "arutyun",
                    "+79202024422",
                    "gevorkyan.arutyun@gmail.com",
                    "Arutyun",
                    "Gevorkyan",
                    "some_location",
                    TimeZone.getDefault(),
                    Gender.MALE,
                    new Date(),
                    passwordEncoder.encode("password"),
                    List.of(roleRepository.findByName(Role.ROLE_OWNER.name()))
            ));

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
