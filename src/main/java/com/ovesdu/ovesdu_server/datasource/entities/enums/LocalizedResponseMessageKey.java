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
    UNKNOWN_ERROR("Unknown error"),
    USERNAME_ALREADY_EXIST("Username already exist"),
    EMAIL_ALREADY_EXIST("Email already exist"),
    PHONE_NUMBER_ALREADY_EXIST("Phone number already exist"),
    DEVICE_ALREADY_EXIST("Device already exist");

    public final String defaultValue;

    LocalizedResponseMessageKey(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
