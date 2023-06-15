package com.kap.kap_server.config.consts;

public abstract class Paths {
    private static final String PATH_API_V_1 = "/api/v1";

    /// PATH AUTH
    public static final String PATH_AUTH = PATH_API_V_1 + "/auth";
    public static final String PATH_AUTH_LOGIN = PATH_AUTH + "/login";
    public static final String PATH_AUTH_REGISTRATION = PATH_AUTH + "/registration";
    public static final String PATH_AUTH_INFO = PATH_AUTH + "/info";

    public static final String PATH_SETTINGS = PATH_API_V_1 + "/settings";
    public static final String PATH_ADMIN = PATH_API_V_1 + "/admin";
    public static final String PATH_MANAGE = PATH_API_V_1 + "/manage";
    public static final String PATH_ROLES = PATH_API_V_1 + "/roles";
}
