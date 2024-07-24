package com.example.credit.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;

/** JwtUtils */
@Slf4j
public class JwtUtil {

    private static final String jwtKey = "BBwEyUjed9tXB24+L+r4O3AHg4BLmqSYm9SAuAMST1Y=";
    private static final int validDate = 1728000000;

    // TODO: Generate jwt using the email , it is done after correct email and
    // password is entered
    public static String generateToken(String username, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return createToken(username, claims);
    }

    private static String createToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validDate))
                .signWith(generateKey())
                .compact();
    }

    private static SecretKey generateKey() {
        return Keys.hmacShaKeyFor(jwtKey.getBytes());
    }

    public static boolean validateToken(String jwtToken) {
        try {
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public static String getToken(String token) {
        return "";
    }

    public static boolean validHeader(String headerAuth) {
        log.info(headerAuth);
        return headerAuth.startsWith("Bearer ") && headerAuth.length() > 7;
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
    }

    public static String extractEmail(String token) {
        return extractAllClaims(token).get("email").toString();
    }

    private static Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
