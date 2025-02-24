package com.example.coffee.utils;

import static io.jsonwebtoken.security.Keys.*;
import static java.nio.charset.StandardCharsets.*;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import javax.crypto.SecretKey;

@ConfigurationProperties(prefix = "jwt")
public record JwtCredentials (
        SecretKey secretKey,
        long accessTokenExp,
        long refreshTokenExp
) {

    @ConstructorBinding
    public JwtCredentials(String secretKey, long accessTokenExp, long refreshTokenExp) {
        this(
                hmacShaKeyFor(secretKey.getBytes(UTF_8)),
                accessTokenExp,
                refreshTokenExp
        );
    }
}
