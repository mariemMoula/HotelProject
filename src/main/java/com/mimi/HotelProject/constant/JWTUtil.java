package com.mimi.HotelProject.constant;

public class JWTUtil {
    public static final Integer EXPIRE_ACCESS_TOKEN_MINUTES = 30;
    public static final Integer EXPIRE_REFRESH_TOKEN_MINUTES = 60;
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ISSUER = "mimiHotel";
    public static final String SECRET = "hotelPwd";
    public static final String AUTH_HEADER = "Authorization";
}
