package com.example.coffee.user.domain;

import com.example.coffee.user.controller.dto.CreateUserRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity // db 테이블과 매핑
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder // .build() 패턴 사용
@Table(name = "users") // 테이블 이름 지정
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 설정
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @Column(length = 500)
    private String refreshToken;

    @Column(length = 500)
    private String accessToken;

    public User(String email, String password, Authority authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

//
//    public void update(CreateUserRequest request) {
//        name = request.name();
//        password = request.password();
//        // return this;
//    }
}
