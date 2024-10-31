package org.askforai._core.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.askforai.user.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY;
    private final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60; // 1시간

    public JwtUtil() {
        String secretKeyEnv = System.getenv("JWT_SECRET_KEY");
        SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyEnv));
    }

    // Access Token 생성
    public String generateAccessToken(User user) {
        return createToken(user, ACCESS_TOKEN_VALIDITY);
    }
    
    // JWT 생성 시 클레임 설정
    private String createToken(User user, long validity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Secret Key와 알고리즘 설정
                .compact();
    }

    // JWT 유효성 검사
    public boolean validateToken(String token) {
    	return !isTokenExpired(token) && isSignatureValid(token);
    }
    
    // JWT 서명 유효성 검사
    public boolean isSignatureValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Secret Key 설정
                .build()
                .parseClaimsJws(token); // 서명 검증
            return true; // 서명이 유효한 경우
        } catch (JwtException e) {
            return false; // 서명이 유효하지 않은 경우
        }
    }
    
    // JWT에서 사용자 ID 추출
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("id", Long.class);
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
