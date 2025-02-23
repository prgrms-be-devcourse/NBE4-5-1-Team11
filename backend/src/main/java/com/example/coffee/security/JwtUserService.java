package com.example.coffee.security;

import com.example.coffee.user.domain.User;
import com.example.coffee.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserService {
    private final UserRepository userRepository;

    public boolean isValidToken(String email, String token) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null || user.getAccessToken() == null) {
            return false;
        }

        return token.equals(user.getAccessToken());
    }
}
