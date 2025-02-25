package com.example.coffee.utils;

import com.example.coffee.user.domain.Authority;
import com.example.coffee.user.domain.Token;
import com.example.coffee.user.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtCredentials jwtCredentials;

    public Token createToken(User user) {
        return new Token(createAccessToken(user), createRefreshToken(user));
    }

    public String createAccessToken(User user) {
        return createJwt(user, jwtCredentials.accessTokenExp());
    }

    public String createRefreshToken(User user) {
        return createJwt(user, jwtCredentials.refreshTokenExp());
    }

    public String createJwt(User user, long expireLength) {
        Date expiration = new Date(System.currentTimeMillis() + expireLength);

        return Jwts.builder()
                .claim("id", user.getId())
                .expiration(expiration)
                .signWith(jwtCredentials.secretKey())
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts
                    .parser()
                    .verifyWith(jwtCredentials.secretKey())
                    .build()
                    .parse(token);
            // token = token.trim(); // 앞뒤 공백 및 개행 문자 제거
            System.out.println("Received Token: [" + token + "]");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public Long extractId(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                .verifyWith(jwtCredentials.secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        } catch (ExpiredJwtException e) {
            return e.getClaims().get("id", Long.class);
        }
        return claims.get("id", Long.class);
    }

    public String extractEmail(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(jwtCredentials.secretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("email", String.class);

        } catch (Exception e) {
            return null;
        }
    }

    public Authority extractAuthority(String token) {
        try {
            String role = Jwts.parser()
                    .verifyWith(jwtCredentials.secretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("authority", String.class);
            return Authority.valueOf(role); // String → Authority 변환
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isExpired(String token) {
        try {
            Date expiration = Jwts.parser()
                    .verifyWith(jwtCredentials.secretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();

            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}