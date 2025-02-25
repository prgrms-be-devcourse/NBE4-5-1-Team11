package com.example.coffee.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 CORS 허용
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 허용할 도메인 (Next.js 프론트엔드 URL)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 허용할 헤더
                .allowCredentials(true) // 쿠키와 인증 정보를 포함한 요청 허용
                .maxAge(3600); // CORS 응답을 캐시할 시간 (초 단위)
    }
}

