package com.example.coffee.user.domain.repository;

import com.example.coffee.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {

    @Test
    @DisplayName("User 객체를 생성할 수 있다.")
    void createUser(){
        User user = new User(1L, "test@example.com", "홍길동", "password123");

        String email = user.getEmail();
        String name = user.getName();
        String password = user.getPassword();

        assertThat(email).isEqualTo("test@example.com");
        assertThat(name).isEqualTo("홍길동");
        assertThat(password).isEqualTo("password123");
    }

    @Test
    @DisplayName("User 객체를 빌터 패턴을 사용하여 생성할 수 있다.")
    void createUserWithBuilder(){
        User user = User.builder()
                .email("builder@example.com")
                .name("이순신")
                .password("securePassword")
                .build();

        String email = user.getEmail();
        String name = user.getName();
        String password = user.getPassword();

        assertThat(email).isEqualTo("builder@example.com");
        assertThat(name).isEqualTo("이순신");
        assertThat(password).isEqualTo("securePassword");
    }
}
