package com.springPractice.common.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
// Spring 이 자동으로 객체 생성, IoC 컨테이너에서 관리 해줌
// IoC 컨테이너 : new 해서 만드는 대신, 스프링이 객체(Bean)를 생성/관리 해주는 공간(저장소)
//    역할1) 객체 생성
//    역할2) 의존성 주입(DI)  -> 필요한 곳에 자동 생성된 Bean 주입
//    역할3) 생명주기 관리  -> 애플리케이션 시작할 때 객체 생성 + 종료할 때 소멸까지 관리
public class PasswordEncoder {

    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}

/*
    [ 비밀번호 인코딩 ]

       1. [config] 패키니 내에 [PasswordEncoder] 클래스 생성
           -> 위 코드 붙여넣기

       2. [build.gradle] 에 의존성 추가
           -> implementation 'at.favre.lib:bcrypt:0.10.2' -> 과제에서는 이거 추가하라고 안내
           // implementation 'org.springframework.boot:spring-boot-starter-security'
           // 두 개중 어떤 걸 쓰는게 좋은지..?

       3. [Service] 에 인코딩 코드 추가
 */