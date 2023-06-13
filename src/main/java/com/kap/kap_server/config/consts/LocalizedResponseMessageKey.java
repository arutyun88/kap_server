package com.kap.kap_server.config.consts;

public enum LocalizedResponseMessageKey {
    SUCCESS("Success"),
    CREATED("Created"),
    APP_LOCALE_HEADER_REQUIRED("app-locale in the header is required"),
    TIME_ZONE_HEADER_REQUIRED("time-zone in the header is required"),
    DEVICE_TYPE_HEADER_REQUIRED("device-type in the header is required"),
    DEVICE_ID_HEADER_REQUIRED("device-id in the header is required"),
    DEVICE_OS_HEADER_REQUIRED("device-os in the header is required"),
    DEVICE_INFORMATION_IS_NOT_VALID("device information is not valid"),
    USER_NOT_FOUND("User not found"),
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password"),
    RESOURCE_NOT_FOUND("Resource not found"),
    UNKNOWN_ERROR("Unknown error"),
    USERNAME_ALREADY_EXIST("Username already exist"),
    EMAIL_ALREADY_EXIST("Email already exist"),
    PHONE_NUMBER_ALREADY_EXIST("Phone number already exist"),
    DEVICE_ALREADY_EXIST("Device already exist"),
    USER_AUTHORIZED("The user is authorized"),
    USER_REGISTERED("The user is registered"),
    TOKEN_INVALID("The token is invalid"),
    TOKEN_NOT_VALID("The token is not valid"),
    ROLE_NOT_FOUND("Role not found"),
    NO_ACCESS_RIGHT("No access right");

    public final String defaultValue;

    LocalizedResponseMessageKey(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
