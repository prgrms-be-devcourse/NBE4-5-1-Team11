package com.example.coffee.user.domain.repository;

import com.example.coffee.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

        @Test
        @DisplayName("회원 저장 및 조회 테스트")
        void saveAndFindUser() {
            User user = User.builder()
                    .email("test@example.com")
                    .name("홍길동")
                    .password("securePassword")
                    .build();

            userRepository.save(user);

            Optional<User> foundUser = userRepository.findByEmail("test@example.com");

            assertThat(foundUser).isPresent();
            assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
            assertThat(foundUser.get().getName()).isEqualTo("홍길동");

        }
    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteUser(){
        User user = User.builder()
                .email("delete@example.com")
                .name("이순신")
                .password("password123")
                .build();

        userRepository.save(user);
        userRepository.delete(user);
        Optional<User> foundUser = userRepository.findByEmail("delete@example.com");
        assertThat(foundUser).isEmpty();
        }
}
