package com.springPractice.schedules.controller;

import com.springPractice.common.ResponseMessage;
import com.springPractice.common.dto.ApiResponse;
import com.springPractice.schedules.dto.ScheduleRequest;
import com.springPractice.schedules.dto.ScheduleResponse;
import com.springPractice.schedules.service.ScheduleService;
import com.springPractice.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
// 컨트롤러 명시 + 응답을 JSON 형태로 반환
@RequiredArgsConstructor
// final, @NonNull 이 붙은 필드를 가진 생성자 자동 생성
public class ScheduleController {

    private final ScheduleService scheduleService;
    //  final : 생성자 통해 초기화 필요 (필수)
    //  서비스 의존성 주입하기 위해서는 @RequiredArgsConstructor 사용하거나 아래처럼 생성자 정의 필요
    //  public ScheduleController(ScheduleService scheduleService) {
    //    this.scheduleService = scheduleService;
    //  }
    private final UserService userService;
    // 유저 관계 매핑 후 추가

    // 일정 생성 //
    @PostMapping("users/{userId}/schedules")
    public ResponseEntity<ApiResponse<ScheduleResponse>> createSchedule(
    // 1. ResponseEntity : 상태코드 지정 가능 (안쓰면 200ok 만 응답 가능), 헤더/바디 전체 제어 가능
    // 2. 공통응답 형식 위해 제네릭 타입 사용 -> ApiResponse : 공통 응답 형식
    //                                   -> ScheduleResponse : 실제 응답 데이터
            @Valid @RequestBody ScheduleRequest scheduleRequest,
            // @Valid : dto 에서 validation 어노테이션 사용할 수 있게 해줌
            // @RequestBody : JSON 요청 데이터 -> 객체 (ScheduleRequest)로 변환
            // scheduleRequest : 변환된 요청 데이터 저장
            @PathVariable Long userId
            // 유저 매핑 후 추가
    ) {
        ScheduleResponse result = scheduleService.createSchedule(scheduleRequest, userId);
        // scheduleService의 createSchedule 메서드 호출 (요청데이터 저장된 scheduleRequest 전달)
        // ScheduleResponse 타입의 변수 result 에 서비스 계층의 반환값(실제 응답 데이터) 저장

        return ResponseEntity.status(ResponseMessage.SUCCESS_CREATE.getStatus())
        // 상태코드 201 지정해서 반환
                .body(ApiResponse.success(ResponseMessage.SUCCESS_CREATE.getMessage(), result));
                // 응답 바디에 공통응답형식인 ApiResponse의 success 메서드 활용
                // result : 실제 응답 데이터
                // .body : 객체를 바디에 넣어줌 (변환은 스프링이 처리)
                // @RestController 덕분에 자동으로 객체가 JSON 으로 응답

    }

    // 일정 전체 조회 //
    @GetMapping("/schedules")
    public ResponseEntity<ApiResponse<List<ScheduleResponse>>> getAllSchedules(
    // 전체 일정을 담을 수 있는 List 추가
    // 조회이기 때문에 요청 데이터가 없어서 @Valid @RequestBody 불필요

    ) {
        List<ScheduleResponse> result = scheduleService.getAllSchedules();

        return ResponseEntity.status(ResponseMessage.SUCCESS_READ.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_READ.getMessage(), result));
    }

    // 일정 단건 조회 //
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> getSchedule(
            @PathVariable Long scheduleId
    ) {
        ScheduleResponse result = scheduleService.getSchedule(scheduleId);

        return ResponseEntity.status(ResponseMessage.SUCCESS_READ.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_READ.getMessage(), result));
    }

    // 일정 수정 //
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> updateSchedule(
            @Valid @RequestBody ScheduleRequest scheduleRequest,
            @PathVariable Long scheduleId
    ) {
        ScheduleResponse result = scheduleService.updateSchedule(scheduleId, scheduleRequest);

        return ResponseEntity.status(ResponseMessage.SUCCESS_UPDATE.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE.getMessage(), result));
    }

    // 일정 삭제 //
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<ApiResponse<Void>> deleteSchedule(
            @PathVariable Long scheduleId
    ) {
        scheduleService.deleteSchedule(scheduleId);

        return ResponseEntity.status(ResponseMessage.SUCCESS_DELETE.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_DELETE.getMessage(), null));
    }
}

