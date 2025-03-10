package com.example.coffee.config;

import com.example.coffee.security.JwtAuthenticationFilter;
import com.example.coffee.user.domain.repository.UserRepository;
import com.example.coffee.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                        .requestMatchers("/users/register", "/users/login", "/users/refresh").permitAll() // 회원가입, 로그인, 토큰 갱신은 허용
                        .requestMatchers(HttpMethod.POST, "/orders").permitAll() // 주문은 전체 허용
                        .requestMatchers(HttpMethod.GET, "/products").permitAll() // 상품 목록 전체 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // 스웨거

                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 페이지 (주문 삭제, 주문 목록 조회 등)
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN") // 상품 생성
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN") // 상품 수정
                        .requestMatchers("/users/list").hasRole("ADMIN") // 🔒 `/users/**` 엔드포인트는 인증 필요
                        .anyRequest().authenticated() // 다른 모든 요청도 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
