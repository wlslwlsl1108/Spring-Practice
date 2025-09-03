package com.springPractice.comments.controller;

import com.springPractice.comments.dto.CommentRequest;
import com.springPractice.comments.dto.CommentResponse;
import com.springPractice.comments.service.CommentService;
import com.springPractice.common.ResponseMessage;
import com.springPractice.common.constant.SessionConstant;
import com.springPractice.common.dto.ApiResponse;
import com.springPractice.schedules.service.ScheduleService;
import com.springPractice.users.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ScheduleService scheduleService;
    private final UserService userService;

    // 댓글 생성
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CommentRequest commentRequest,
            HttpSession session
    ) {
        // 로그인한 사용자 ID (작성자)
        Long loginUserId = (Long) session.getAttribute(SessionConstant.SESSION_USER);

        // 댓글 생성 (작성자 = 로그인한 사용자)
        CommentResponse result = commentService.createComment(scheduleId, loginUserId, commentRequest);

        return ResponseEntity.status(ResponseMessage.SUCCESS_CREATE.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_CREATE.getMessage(), result));
    }
}
