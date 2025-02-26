package com.example.coffee.user.service;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.controller.dto.LoginRequest;
import com.example.coffee.user.controller.dto.TokenResponse;
import com.example.coffee.user.domain.Authority;
import com.example.coffee.user.domain.Token;
import com.example.coffee.user.domain.User;
import com.example.coffee.user.domain.repository.UserRepository;
import com.example.coffee.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 인코딩 해야 함
    private final JwtUtil jwtUtil;

    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest userDto){
        String encodedPassword = passwordEncoder.encode(userDto.password()); // 비밀번호 인코딩

        User user = userRepository.findByEmail(userDto.email())
                .map(existingUser -> {
                    if (!existingUser.getAuthority().equals(Authority.ROLE_REGISTERED)) {
                        throw new IllegalStateException("이미 회원가입한 회원입니다.");
                    }
                    existingUser.updateAuthority(Authority.ROLE_USER);
                    existingUser.updatePassword(encodedPassword);
                    return existingUser;
                })
                .orElseGet(() -> userRepository.save(userDto.toEntity(encodedPassword)));

        return CreateUserResponse.from(user);
    }

    @Transactional
    public TokenResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getAuthority().equals(Authority.ROLE_REGISTERED)) {
            throw new IllegalArgumentException("회원가입이 필요합니다.");
        }

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Wrong password");
        }

        Token token = jwtUtil.createToken(user);
        user.updateRefreshToken(token.refreshToken());

        return TokenResponse.from(token);
    }

    @Transactional
    public TokenResponse refreshAccessToken(String refreshToken) {
        // refresh token 유효성 검사
        if (!jwtUtil.isValidToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // user 찾기
        Long userId = jwtUtil.extractId(refreshToken);
        if (userId == null) {
            throw new IllegalArgumentException("Invalid refresh token: userId is null");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // user의 refresh token과 동일한지 확인
        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("Refresh token mismatch");
        }

        // 토큰 재발행 및 user의 refresh token 업데이트
        Token token = jwtUtil.createToken(user);
        user.updateRefreshToken(token.refreshToken());

        return TokenResponse.from(token);
    }


    public List<CreateUserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(CreateUserResponse::from)
                .toList();
    }

    public CreateUserResponse getUserById(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return CreateUserResponse.from(foundUser);
    }

    public CreateUserResponse getUserByEmail(String email) {
        User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return CreateUserResponse.from(foundUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteCurrentUser(User user) {
        user.updateAuthority(Authority.ROLE_REGISTERED);
        userRepository.save(user);
    }
}