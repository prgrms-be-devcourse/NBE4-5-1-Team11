package com.example.coffee.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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
    @Column(nullable = false, length = 20)
    private Authority authority;

    @Column(length = 500)
    private String refreshToken;  // ✅ 리프레시 토큰 저장

    public User(String email, String password, Authority authority) {
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public User(String email) {
        this.email = email;
        this.password = email + UUID.randomUUID();
        this.authority = Authority.ROLE_REGISTERED;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateAuthority(Authority authority) {
        this.authority = authority;
    }

//
//    public void update(CreateUserRequest request) {
//        name = request.name();
//        password = request.password();
//        // return this;
//    }
}
