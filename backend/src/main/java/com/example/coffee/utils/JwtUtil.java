package com.example.coffee.utils;

import com.example.coffee.user.domain.Authority;
import com.example.coffee.user.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
//    public static class Json {
//
//        private static final ObjectMapper objectMapper = new ObjectMapper();
//
//        public static String toString(Object obj) {
//            try {
//                return objectMapper.writeValueAsString(obj);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public static class Jwt {
        public static String createToken(String keyString, int expireSeconds, Map<String, Object> claims) {

            SecretKey secretKey = Keys.hmacShaKeyFor(keyString.getBytes());

            Date issuedAt = new Date();
            Date expiration = new Date(issuedAt.getTime() + 1000L * expireSeconds);

            String jwt = Jwts.builder()
                    .claims(claims)
                    .issuedAt(issuedAt)
                    .expiration(expiration)
                    .signWith(secretKey)
                    .compact();

            return jwt;
        }

        public static boolean isValidToken(String keyString, String token) {
            try {

                SecretKey secretKey = Keys.hmacShaKeyFor(keyString.getBytes());

                Jwts
                        .parser()
                        .verifyWith(secretKey)
                        .build()
                        .parse(token);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;

        }

        public static String extractEmail(String keyString, String token) {
            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(keyString.getBytes());
                return Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject();
            } catch (Exception e) {
                return null;
            }
        }

        public static boolean isExpired(String keyString, String token) {
            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(keyString.getBytes());

                Date expiration = Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getExpiration();

                return expiration.before(new Date());
            } catch (Exception e) {
                return true;
            }
        }

        public static String createRefreshToken(String keyString, int expireDays) {
            SecretKey secretKey = Keys.hmacShaKeyFor(keyString.getBytes());

            Date issuedAt = new Date();
            Date expiration = new Date(issuedAt.getTime() + 1000L * 60 * 60 * 24 * expireDays);

            return Jwts.builder()
                    .subject("refresh-token")
                    .issuedAt(issuedAt)
                    .expiration(expiration)
                    .signWith(secretKey)
                    .compact();
        }

        public static void setAuthentication(String email) {
            // 1️⃣ Spring Security에서 사용할 User 객체 생성
            User user = new User(email, "", Authority.ROLE_USER);

            // 2️⃣ 인증 객체 생성 (비밀번호는 null, 권한은 빈 리스트)
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

            // 3️⃣ SecurityContext에 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
        }




//        public static Map<String, Object> getPayload(String keyString, String jwtStr) {
//
//            SecretKey secretKey = Keys.hmacShaKeyFor(keyString.getBytes());
//
//            return (Map<String, Object>) Jwts
//                    .parser()
//                    .verifyWith(secretKey)
//                    .build()
//                    .parse(jwtStr)
//                    .getPayload();
//
//        }
    }


}