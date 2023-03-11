package com.ovesdu.ovesdu_server.datasource.entities.enums;

public enum LocalizedResponseMessageKey {
    SUCCESS("Success"),
    CREATED("Created"),
    APP_LOCALE_HEADER_REQUIRED("app-locale in the header is required"),
    DEVICE_TYPE_HEADER_REQUIRED("device-type in the header is required"),
    DEVICE_ID_HEADER_REQUIRED("device-id in the header is required"),
    DEVICE_OS_HEADER_REQUIRED("device-os in the header is required"),
    DEVICE_INFORMATION_IS_NOT_VALID("device information is not valid"),
    USER_NOT_FOUND("User not found"),
    RESOURCE_NOT_FOUND("Resource not found"),
    UNKNOWN_ERROR("Unknown error");

    public final String defaultValue;

    LocalizedResponseMessageKey(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
