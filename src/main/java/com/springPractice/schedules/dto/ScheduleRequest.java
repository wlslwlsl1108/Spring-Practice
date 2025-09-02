package com.springPractice.schedules.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequest {

    @NotNull
    @Size(max = 100)
    private final String title;
    private final String content;
    // DTO는 요청 시에는 그릇처럼 담는 역할 ,
    //       응답 시에는 전달해주는 역할만 하지 값을 바꾸는 로직을 수행하지 않으므로 final로 지정 가능
    // final = 불변성 보장 -> 코드 안정성 증가
}
