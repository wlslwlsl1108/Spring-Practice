package com.springPractice.schedules.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequest {

    @NotNull
    @Size(max = 100)
    private String title;
    private String content;
}
