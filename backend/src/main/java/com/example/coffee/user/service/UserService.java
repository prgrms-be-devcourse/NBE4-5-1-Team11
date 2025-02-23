package com.example.coffee.user.service;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.controller.dto.LoginRequest;
import com.example.coffee.user.controller.dto.LoginResponse;
import com.example.coffee.user.domain.repository.UserRepository;
import com.example.coffee.user.domain.User;
import com.example.coffee.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 인코딩 해야 함
    private final String secretKey = "your-base64-encoded-secret-key-here";

    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest userDto){
        String encodedPassword = passwordEncoder.encode(userDto.password()); // 비밀번호 인코딩
        return CreateUserResponse.from(userRepository.save(userDto.toEntity(encodedPassword))); // 넣어서 dto 반환
    }

    @Transactional
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Wrong password");
        }

        Map<String, Object> claims = Map.of("email", user.getEmail(), "authority", user.getAuthority().name());
        String accessToken = JwtUtil.Jwt.createToken(secretKey, 3600, claims);
        String refreshToken = JwtUtil.Jwt.createRefreshToken(secretKey, 7, claims);

        user.updateRefreshToken(refreshToken);
        userRepository.save(user);

        return new LoginResponse(accessToken, refreshToken);
     }

    @Transactional
    public LoginResponse refreshAccessToken(String refreshToken) {
        if (!JwtUtil.Jwt.isValidToken(secretKey, refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = JwtUtil.Jwt.extractEmail(secretKey, refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("Refresh token mismatch");
        }

        Map<String, Object> claims = Map.of(
                "email", user.getEmail(),
                "authority", user.getAuthority().name()
        );
        String newAccessToken = JwtUtil.Jwt.createToken(secretKey, 3600, claims);

        return new LoginResponse(newAccessToken, refreshToken);
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
}
