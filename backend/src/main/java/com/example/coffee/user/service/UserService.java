package com.example.coffee.user.service;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.domain.repository.UserRepository;
import com.example.coffee.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest userDto){
        return CreateUserResponse.from(userRepository.save(userDto.toEntity()));
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
    public CreateUserResponse updateUser(Long id, CreateUserRequest userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.update(userDto);

        return CreateUserResponse.from(userRepository.save(user)); // 변경 사항을 저장
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
