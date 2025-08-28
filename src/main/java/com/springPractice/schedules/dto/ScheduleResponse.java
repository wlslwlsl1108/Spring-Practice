package com.springPractice.schedules.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor                           // 아래 모든 필드를 매개변수로 갖는 생성자 자동 생성 -> 직접 생성할 경우 오류발생
@JsonInclude(JsonInclude.Include.NON_NULL)    // NULL인 필드는 응답에서 제외(생략)
public class ScheduleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}
