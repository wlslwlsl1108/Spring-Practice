package com.springPractice.schedules.controller;

import com.springPractice.common.dto.ApiResponse;
import com.springPractice.schedules.dto.ScheduleRequest;
import com.springPractice.schedules.dto.ScheduleResponse;
import com.springPractice.schedules.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController           // 컨트롤러 명시
@RequiredArgsConstructor     // final, NonNull 필드 대상 생성자 자동 생성
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성 //
    @PostMapping("/schedules")
    public ResponseEntity<ApiResponse<ScheduleResponse>> createSchedule(
            @Valid @RequestBody ScheduleRequest scheduleRequest
    ) {
        ScheduleResponse result = scheduleService.createSchedule(scheduleRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("일정 생성 성공", result));
    }


}
