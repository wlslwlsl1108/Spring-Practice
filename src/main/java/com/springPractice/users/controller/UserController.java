package com.springPractice.users.controller;

import com.springPractice.common.ResponseMessage;
import com.springPractice.common.dto.ApiResponse;
import com.springPractice.users.dto.UserRequest;
import com.springPractice.users.dto.UserResponse;
import com.springPractice.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성 //
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest userRequest
    ) {
        UserResponse result = userService.createUser(userRequest);

        return ResponseEntity.status(ResponseMessage.SUCCESS_CREATE.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_CREATE.getMessage(), result));
    }
}
