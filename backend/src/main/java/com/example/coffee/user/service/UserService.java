package com.example.coffee.user.service;

import com.example.coffee.user.domain.repository.UserRepository;
import com.example.coffee.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.save(userRepository.findById(id).orElseThrow().update(updatedUser.getName(), updatedUser.getPassword()));
        // 너무 길어도 괜찮을까요?
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
