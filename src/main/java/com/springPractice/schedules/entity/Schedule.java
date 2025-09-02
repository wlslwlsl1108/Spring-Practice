package com.springPractice.schedules.entity;

import com.springPractice.common.entity.BaseEntity;
import com.springPractice.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    /*@ManyToOne(fetch = FetchType.LAZY)                // N : 1 -> fetch 속성(조회 시점) = LAZY(지연로딩)
    @JoinColumn(name = "user_id", nullable = false)   // user_id 라는 외래키 생성
    private User user;                                // user 엔티티 참조
    user 생성 후 추가 예정*/

    // 생성자 정의 //
    public Schedule(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 일정 수정 //
    public void updateSchedule(String title, String content) {
        this.title = title;
        this.content = content;
    }

}

/*
    [LAZY (지연 로딩)]
       - 연관된 엔티티를 실제 사용할 때까지 조회 X

    [EAGER (즉시 로딩)]
       - 연관된 엔티티를 즉시 함께 조회

 */