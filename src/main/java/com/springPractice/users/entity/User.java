package com.springPractice.users.entity;

import com.springPractice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity              // 엔티티 클래스 표시
@Getter              // getter 메서드 자동 생성
@NoArgsConstructor   // 기본 생성자 자동 생성
public class User extends BaseEntity {

    @Id                                                         // PK 표시 및 DB 테이블에서 PK와 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // PK 값 자동 생성
    private Long id;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
