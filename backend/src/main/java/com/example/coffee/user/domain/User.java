package com.example.coffee.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity // db 테이블과 매핑
@Getter
@Setter
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
    private String name;

    @Column(nullable = false)
    private String password;
}
