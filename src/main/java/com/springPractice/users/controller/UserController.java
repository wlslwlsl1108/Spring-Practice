package com.springPractice.users.controller;

import com.springPractice.common.ResponseMessage;
import com.springPractice.common.constant.SessionConstant;
import com.springPractice.common.dto.ApiResponse;
import com.springPractice.users.dto.*;
import com.springPractice.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 유저 로그인 //
    @PostMapping("/users/login")
    public ResponseEntity<ApiResponse<UserLoginResponse>> login(
            @Valid @RequestBody UserLoginRequest userLoginRequest,
            HttpServletRequest request
    ) {
        UserLoginResponse result = userService.login(userLoginRequest);

        // Cookie Session 발급 //
        HttpSession session = request.getSession();
        session.setAttribute(
                SessionConstant.SESSION_USER, result.getUserId());

        return ResponseEntity.status(ResponseMessage.SUCCESS_LOGIN.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_LOGIN.getMessage(), result));
    }

    // 유저 전체 조회 //
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        List<UserResponse> result = userService.getAllUsers();

        return ResponseEntity.status(ResponseMessage.SUCCESS_READ.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_READ.getMessage(), result));
    }

    // 유저 단건 조회 //
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(
            @PathVariable Long userId
    ) {
        UserResponse result = userService.getUser(userId);

        return ResponseEntity.status(ResponseMessage.SUCCESS_READ.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_READ.getMessage(), result));
    }

    // 유저 수정 //
    @PutMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @Valid @RequestBody UserUpdateRequest userUpdateRequest,
            @PathVariable Long userId
    ) {
        UserResponse result = userService.updateUser(userId, userUpdateRequest);

        return ResponseEntity.status(ResponseMessage.SUCCESS_UPDATE.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_UPDATE.getMessage(), result));
    }

    // 유저 삭제 //
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(
            @PathVariable Long userId
    ) {
        userService.deleteUser(userId);

        return ResponseEntity.status(ResponseMessage.SUCCESS_DELETE.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_DELETE.getMessage(), null));
    }

}
