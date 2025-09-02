package com.springPractice.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
// "스프링 설정 클래스" 명시 => 환경 설정용으로 사용
// 스프링 실행 시, 해당 클래스 안의 설정(Bean 등록 등)을 스프링 컨테이너가 읽어 적용
@EnableJpaAuditing
// JPA Auditing 기능 활성화
// @CreatedDate, @LastModifiedDate 같은 어노테이션 동작할 수 있게 해줌
// => 이걸 붙여야 BaseEntity 에서 작성한 생성일/수정일 자동 저장이 실제 DB에 반영됨
public class JpaConfIg {
}
