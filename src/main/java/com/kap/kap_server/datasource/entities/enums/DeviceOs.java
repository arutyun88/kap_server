package com.kap.kap_server.datasource.entities.enums;

import java.util.Arrays;

public enum DeviceOs {
    IOS("ios"),
    ANDROID("android"),
    WEB("web");

    public final String name;

    DeviceOs(String name) {
        this.name = name;
    }

    public static DeviceOs getByName(String name) {
        return Arrays.stream(DeviceOs.values()).
                filter(deviceOs -> deviceOs.name.equals(name)).
                findFirst().orElse(null);
    }

    public static boolean validate(String name, DeviceType type) {
        final DeviceOs os = getByName(name);
        if (os != null && type != null) {
            switch (type) {
                case TABLET, MOBILE -> {
                    return os == DeviceOs.IOS || os == DeviceOs.ANDROID;
                }
                case DESKTOP -> {
                    return os == DeviceOs.WEB;
                }
            }
        }
        return false;
    }
}