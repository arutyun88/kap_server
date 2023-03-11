package com.ovesdu.ovesdu_server.datasource.entities.enums;

public enum DeviceOs {
    IOS("ios"),
    ANDROID("android"),
    WEB("web");

    public final String name;

    DeviceOs(String name) {
        this.name = name;
    }

    public static boolean contains(String name) {
        for (DeviceOs type : DeviceOs.values()) {
            if (type.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
}