package com.springPractice.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass                                  // 테이블 생성 없이, 상속받은 자식 엔티티들의 테이블에 컬럼이 포함됨
@EntityListeners(AuditingEntityListener.class)     // Auditing 기능 활성화하여 생성일/수정일 자동 관리
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false)   // insert 시점에만 기록됨 -> update 시점에서 변경 x
    @Temporal(TemporalType.TIMESTAMP)              // 년-월-일 시:분:초 까지 DB에 저장
    private LocalDateTime createdAt;

    @LastModifiedDate                              // update 할 때마다 자동 갱신
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

}
