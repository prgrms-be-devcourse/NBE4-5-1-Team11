package com.example.coffee.security;

import com.example.coffee.user.domain.User;
import com.example.coffee.user.domain.repository.UserRepository;
import com.example.coffee.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 여기서 token이 null이면 바로 doFilter() 부르고 끝내야할 것 같아요!
        // 토큰이 없으면 이 필터에 더 있을 필요가 없으니깐 ~.~

        // 아 글쿤요ㅋㅋㅋㅋㅋㅋ 아 넵넵 제가 해볼게요!! 진짜 감사합니다 하연님ㅋㅋㅋㅋㅋ
        /* 여기서부터 이유 설명!!
        프론트에서 요청이 들어오면 무조건 securityConfig의 securityFilterChain을 거치게 됩니다
        (정확한 건 아니어도 대강 그렇게 생각해주시면 편해요 어쨌든 등록한 ~filter들은 무조건 모두 거치게 됨)
        그런데 우리는 addFilterBefore()로 이 JwtAuthenticationFilter를 추가해놨잖아요
        그러면 모든 요청은 이 JwtAuthenticationFilter으로 들어오게 돼요
        로그인 전 같이 토큰이 없는 경우는 request.getHeader("Authorization")에서 null을 받게 되기 때문에
        이 경우엔 미리 doFilter()를 호출해서 나머지 로직이 실행되지 않도록 해야합니다요
         */

//        token = token.substring(7);

        Long id = jwtUtil.extractId(token);
        if (id != null) {
            setAuthentication(id); // 유저가 리스트에 있는지만 조회하면 됨, pw 필요 없음
        }

        filterChain.doFilter(request, response);
    }
    // 끝
    // 오 끝났어요?????
    // 감사합니다 웅빈님!! 테스트 해볼게요!!!!
    // 진짜 끝이에요
    // 오ㅋㅋㅋㅋㅋㅋㅋㅋ 와 진짜 고생하셨어요!! 얼른 테스트 해보겠습니다!! 👍
    public void setAuthentication(Long id) {
        User user = userRepository.getById(id);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getAuthority().name()));

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
