package com.eking.momp.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static String signKey = "test1";

    public static String createJwt(String id, String subject, Map<String, Object> claims, Duration duration) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + duration.toMillis());
        return Jwts.builder()
                .setId(id)
                .setClaims(claims)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(expiration)
                .compact();
    }
    
    public static Claims parseJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
