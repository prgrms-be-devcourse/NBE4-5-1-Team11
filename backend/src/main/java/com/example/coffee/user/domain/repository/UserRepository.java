package com.example.coffee.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.coffee.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);
}
