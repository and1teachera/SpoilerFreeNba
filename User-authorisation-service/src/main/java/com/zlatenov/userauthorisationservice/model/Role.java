package com.zlatenov.userauthorisationservice.model;

public enum Role  {

    ADMIN(Authority.ADMIN),
    ROLE_SUPER_ADMIN(Authority.ROLE_SUPER_ADMIN),
    USER(Authority.USER);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    //@Override
    public String getAuthority() {
        return authority;
    }

    public static final class Authority {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
        public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    }
}