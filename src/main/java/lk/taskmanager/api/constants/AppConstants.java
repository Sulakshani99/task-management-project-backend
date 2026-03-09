package lk.taskmanager.api.constants;

public class AppConstants {

    public static class DetailConstants {
        public static final String ASYNC_EXECUTOR = "taskAsyncExecutor";
        public static final String APP_NAME = "Task Management System";
        public static final String APP_VERSION = "1.0.0";
    }

    public static class SecurityConstants {
        public static final String ADMIN_CLIENT_ID = "admin-client";
        public static final String USER_CLIENT_ID = "user-client";
        public static final int ADMIN_TOKEN_VALIDITY = 3600; // 1 hour
        public static final int USER_TOKEN_VALIDITY = 7200;  // 2 hours
        public static final int REFRESH_TOKEN_VALIDITY = 86400; // 24 hours
    }

    public static class PaginationConstants {
        public static final int DEFAULT_PAGE = 0;
        public static final int DEFAULT_SIZE = 10;
        public static final int MAX_SIZE = 100;
    }
}
