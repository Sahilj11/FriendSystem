package com.example.credit.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtUtils
 */
@Slf4j
public class JwtUtil {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String jwtKey = "BBwEyUjed9tXB24+L+r4O3AHg4BLmqSYm9SAuAMST1Y=";
    private static final int validDate = 1728000000;

    public static String generateToken(String username, String email, int uId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("uId", uId);
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

    public static boolean validHeader(String headerAuth) {
        if (headerAuth.startsWith("Bearer ") && headerAuth.length() > 7) return true;
        log.warn("Header provided {}", headerAuth);
        return false;
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
    }

    public static String extractEmail(String token) {
        return extractAllClaims(token).get("email").toString();
    }

    public static int extractId(String token) {
        String id = extractAllClaims(token).get("uId").toString();
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException ne) {
            log.warn("Entered uID value {} is not a number", id);
            return -1;
        }
    }

    private static Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
