package com.jskno.managinglistsbe.security.utils;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/users/**";
    public static final String LOG_IN_URLS = "/authentication/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 3_600_000; // 30_000 =  30 seconds

    public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_AUDIENCE = "audience";
    public static final String CLAIM_KEY_CREATED = "created";
    public static final String CLAIM_USER_DETAILS = "userDetails";
}
