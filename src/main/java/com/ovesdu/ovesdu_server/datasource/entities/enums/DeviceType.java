package com.ovesdu.ovesdu_server.datasource.entities.enums;

public enum DeviceType {
    MOBILE("mobile"),
    TABLET("tablet"),
    DESKTOP("desktop");

    public final String name;

    DeviceType(String name) {
        this.name = name;
    }

    public static boolean contains(String name) {
        for (DeviceType type : DeviceType.values()) {
            if (type.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
}