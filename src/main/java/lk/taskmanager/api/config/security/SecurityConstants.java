package lk.taskmanager.api.config.security;

public class SecurityConstants {

    public static final String TOKEN_SIGN_IN_KEY = "taskmanagement-secret-key-2024";

    public static final String ADMIN_ID = "admin-client";
    public static final String USER_ID = "user-client";

    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String IMPLICIT = "implicit";

    public static final String SCOPE_READ = "read";
    public static final String SCOPE_WRITE = "write";
    public static final String TRUST = "trust";

    public static final int ADMIN_ACCESS_TOKEN_VALIDITY_SECONDS = 3600;
    public static final int USER_ACCESS_TOKEN_VALIDITY_SECONDS = 7200;
    public static final int ADMIN_REFRESH_TOKEN_VALIDITY_SECONDS = 86400;
    public static final int USER_REFRESH_TOKEN_VALIDITY_SECONDS = 86400;
}
