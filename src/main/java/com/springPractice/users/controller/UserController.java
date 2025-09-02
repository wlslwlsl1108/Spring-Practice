package com.springPractice.users.controller;

import com.springPractice.common.ResponseMessage;
import com.springPractice.common.dto.ApiResponse;
import com.springPractice.users.dto.UserRequest;
import com.springPractice.users.dto.UserResponse;
import com.springPractice.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    // 유저 전체 조회 //
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        List<UserResponse> result = userService.getAllUsers();

        return ResponseEntity.status(ResponseMessage.SUCCESS_READ.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_READ.getMessage(), result));
    }
}
