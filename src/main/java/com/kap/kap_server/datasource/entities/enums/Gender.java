package com.kap.kap_server.datasource.entities.enums;

import java.util.Arrays;

public enum Gender {
    MALE("male"),
    FEMALE("female");

    public final String name;

    Gender(String name) {
        this.name = name;
    }

    public static Gender getByName(String name) {
        return Arrays.stream(Gender.values()).filter(gender -> gender.name.equals(name)).findFirst().orElse(null);
    }
}
