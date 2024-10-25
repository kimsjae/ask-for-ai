package org.askforai._core.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomKeyGenerator {
    public static void main(String[] args) {
        // 256비트 (32바이트) 랜덤 키 생성
        byte[] randomKey = new byte[32]; // 32 바이트
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomKey);

        // Base64로 인코딩
        String encodedKey = Base64.getEncoder().encodeToString(randomKey);
        
        // 생성된 키 출력
        System.out.println("Generated Base64 Encoded Key: " + encodedKey);
    }
}
