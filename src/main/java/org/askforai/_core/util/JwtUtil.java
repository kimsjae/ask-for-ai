package org.askforai._core.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY;
    private final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60; // 1시간
//    private final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 30; // 30일

    public JwtUtil() {
        String secretKeyEnv = System.getenv("JWT_SECRET_KEY");
        SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyEnv));
    }

    // Access Token 생성
    public String generateAccessToken(String username) {
        return createToken(username, ACCESS_TOKEN_VALIDITY);
    }

//    // Refresh Token 생성
//    public String generateRefreshToken(String username) {
//        return createToken(username, REFRESH_TOKEN_VALIDITY);
//    }
    
    // JWT 생성 시 클레임 설정
    private String createToken(String username, long validity) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Secret Key와 알고리즘 설정
                .compact();
    }

    // JWT 유효성 검사
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // JWT에서 사용자 이름 추출
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // JWT에서 모든 클레임 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Secret Key 설정
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 만료 여부 확인
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
