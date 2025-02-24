package com.example.coffee.config;

import com.example.coffee.utils.JwtCredentials;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        JwtCredentials.class,
})
public class CredentialConfig {
}
