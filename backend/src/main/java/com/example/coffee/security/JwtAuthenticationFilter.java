package com.example.coffee.security;

import com.example.coffee.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String secretKey;

    public JwtAuthenticationFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1️⃣ 요청에서 Authorization 헤더 가져오기
        String token = request.getHeader("Authorization");

        // 2️⃣ JWT가 없거나 "Bearer "로 시작하지 않으면 필터 통과
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3️⃣ "Bearer " 부분 제거 후 JWT만 남김
        token = token.substring(7);

        // 4️⃣ 토큰이 유효한지 확인
        if (!JwtUtil.Jwt.isValidToken(secretKey, token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5️⃣ JWT에서 이메일(사용자 아이디) 추출
        String email = JwtUtil.Jwt.extractEmail(secretKey, token);
        if (email != null) {
            JwtUtil.Jwt.setAuthentication(email); // ✅ 사용자 인증 정보 설정
        }

        // 6️⃣ 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }


}
