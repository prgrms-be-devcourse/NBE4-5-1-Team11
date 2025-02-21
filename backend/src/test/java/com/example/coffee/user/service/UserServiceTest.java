package com.example.coffee.user.service;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import com.example.coffee.user.controller.dto.CreateUserResponse;
import com.example.coffee.user.domain.User;
import com.example.coffee.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        User user1 = User.builder()
                .email("test1@example.com")
                .name("홍길동")
                .password("password123")
                .build();

        User user2 = User.builder()
                .email("test2@example.com")
                .name("이순신")
                .password("password456")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }

//    @AfterEach
//    void cleanUp(){
//        userRepository.deleteAll();
//    }

    @Test
    @DisplayName("회원 저장 테스트")
     void saveUser(){
        CreateUserRequest userDto = CreateUserRequest.builder()
                .email("new@example.com")
                .name("강감찬")
                .password("securePass")
                .build();

        CreateUserResponse response = userService.saveUser(userDto);

        Optional<User> foundUser = userRepository.findByEmail("new@example.com");
        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("강감찬");
    }

    @Test
    @DisplayName("모든 회원 조회 테스트")
    void getAllUsers() {
        List<CreateUserResponse> users = userService.getAllUsers();

        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("회원 ID로 조회 테스트")
    void getUserById(){
        User savedUser = userService.getUserByEmail("test1@example.com");
        CreateUserResponse response = userService.getUserById(savedUser.getId());

        assertThat(response.email()).isEqualTo("test1@example.com");
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void updateUser(){
        User existingUser = userService.getUserByEmail("test1@example.com");
        Long userId = existingUser.getId();

        CreateUserRequest userDto = CreateUserRequest.builder()
                .email("updated@example.com")
                .name("변경된 홍길동")
                .password("moreSecurePassword")
                .build();

        CreateUserResponse response = userService.updateUser(userId, userDto);

        assertThat(response.name()).isEqualTo("변경된 홍길동");
        assertThat(response.password()).isEqualTo("moreSecurePassword");
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteUser() {
        User userToDelete = userService.getUserByEmail("test1@example.com");
        Long userId = userToDelete.getId();

        userService.deleteUser(userId);

        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(RuntimeException.class)  // 💡 예외 발생 검증
                .hasMessageContaining("User not found"); // 💡 예외 메시지 검증
    }
}
