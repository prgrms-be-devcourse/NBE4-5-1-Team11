package com.example.coffee.user.service;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.controller.dto.LoginRequest;
import com.example.coffee.user.controller.dto.TokenResponse;
import com.example.coffee.user.domain.User;
import com.example.coffee.user.domain.repository.UserRepository;
import com.example.coffee.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return CreateUserResponse.from(userRepository.save(userDto.toEntity(encodedPassword))); // 넣어서 dto 반환
    }

    @Transactional
    public TokenResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Wrong password");
        }


        return TokenResponse.from(jwtUtil.createToken(user));
     }

    @Transactional
    public TokenResponse refreshAccessToken(String refreshToken) {
        if (!jwtUtil.isValidToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        Long userId = jwtUtil.extractId(refreshToken);
        if (userId == null) {
            throw new IllegalArgumentException("Invalid refresh token: userId is null");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        if (!user.getRefreshToken().equals(refreshToken)) {
//            throw new RuntimeException("Refresh token mismatch");
//        }

        String newAccessToken = jwtUtil.createAccessToken(user);

        return new TokenResponse(newAccessToken, refreshToken);
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
