package com.example.coffee.security;

import com.example.coffee.user.domain.Authority;
import com.example.coffee.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);

        if (!JwtUtil.Jwt.isValidToken(secretKey, token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = JwtUtil.Jwt.extractEmail(secretKey, token);
        Authority authority = JwtUtil.Jwt.extractAuthority(secretKey, token);

        if (email != null && authority != null) {
            JwtUtil.Jwt.setAuthentication(email, Authority.valueOf(authority.name()));
        }

        filterChain.doFilter(request, response);
    }

}
