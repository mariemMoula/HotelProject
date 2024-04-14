package com.mimi.HotelProject.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mimi.HotelProject.constant.JWTUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JWTHelper {

    final Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);

    public String generateAccessToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(DateUtils.addMinutes(new Date(), JWTUtil.EXPIRE_ACCESS_TOKEN_MINUTES))
                .withIssuer(JWTUtil.ISSUER)
                .withClaim("role", role)
                .sign(algorithm);
    }

    public String generateRefreshToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(DateUtils.addMinutes(new Date(), JWTUtil.EXPIRE_REFRESH_TOKEN_MINUTES))
                .withIssuer(JWTUtil.ISSUER)
                .sign(algorithm);
    }

    public String extractTokenFromHeaderIfExists(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(JWTUtil.BEARER_PREFIX)) {
            return authorizationHeader.substring(JWTUtil.BEARER_PREFIX.length());
        }
        return null;
    }

    public Map<String, String> getTokensMap(String jwtAccessToken, String jwtRefreshToken) {
        Map<String, String> idTokens = new HashMap<>();
        idTokens.put("accessToken", jwtAccessToken);
        idTokens.put("refreshToken", jwtRefreshToken);
        return idTokens;
    }
}
