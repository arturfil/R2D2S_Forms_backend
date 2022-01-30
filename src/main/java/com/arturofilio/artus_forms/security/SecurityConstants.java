package com.arturofilio.artus_forms.security;

import com.arturofilio.artus_forms.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_DATE = 864000000; // 10 days
    public static final String LOGIN_URL = "/api/users/login";
    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
