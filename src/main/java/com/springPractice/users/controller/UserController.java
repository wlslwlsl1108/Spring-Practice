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
            // 인터페이스(HttpServletRequest) 타입의 변수 선언 //
            // -> POST /users/login 요청 보내면 객체 생성됨
            HttpServletRequest request
            // HTTP 요청 정보를 보내주어야, 세션의 유무를 확인 가능하기 때문에 필요!
            /* HttpServletRequest : 요청 정보를 다룰 수 있는 미리 만들어진 인터페이스
                -> 클래스 및 객체 직접 구현 x (톰캣/제티 같은 서버가 구현 + 스프링에 의해 자동 주입)
                -> HTTP 요청 전체 정보가 들어가 있으며, 이 객체 안에서 정보를 조회 가능
                # 주로 사용 메서드 : getParameter(), getSession(), getHeader()
             */
    ) {
        UserLoginResponse result = userService.login(userLoginRequest);

        // Cookie Session 발급 //
        HttpSession session = request.getSession();
        // HttpSession : 서블릿 표준에서 기본 제공하는 인터페이스
        // request.getSession() 호출 시
        //   1. 세션 있는 경우 -> 기존 세션 반환
        //   2. 세션 없는 경우 -> 새로운 세션 만들어 반환 (세션 발급)

        // 세션에 SESSION_USER 이름으로 로그인한 사용자 ID 저장 //
        session.setAttribute(
                SessionConstant.SESSION_USER, result.getUserId());
        /* session.setAttribute(key, value)
             - 세션(HttpSession) 데이터 저장하는 메서드
             - Key : 문자열 (이름표 역할)
             - value : 실제 저장 데이터 (Object 타입 -> 어떤 타입이든 저장 가능)
         */

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

    // 내 정보 조회 //
    // "/users/{userId}"  ->  "/users/me" 변경
    @GetMapping("/users/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo(HttpSession session) {

        // 1. 세션에서 로그인한 사용자 ID 꺼내와 저장 //
        Long loginUserId = (Long) session.getAttribute(SessionConstant.SESSION_USER);

        // 2. 사용자 ID로 DB 에서 정보 조회 //
        UserResponse result = userService.getUser(loginUserId);

        // 3. 조회된 사용자 정보 응답 //
        return ResponseEntity.status(ResponseMessage.SUCCESS_READ.getStatus())
                .body(ApiResponse.success(ResponseMessage.SUCCESS_READ.getMessage(), result));
    }

    // 내 정보 수정 //
    // "/users/{userId}"  ->  "/users/me" 변경
    @PutMapping("/users/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @Valid @RequestBody UserUpdateRequest userUpdateRequest,
            HttpSession session
    ) {
        // 1. 세션에서 로그인한 사용자 ID 꺼내와 저장 //
        Long loginUserId = (Long) session.getAttribute(SessionConstant.SESSION_USER);

        // 2. 사용자 ID로 DB 에서 정보 조회 //
        UserResponse result = userService.updateUser(loginUserId, userUpdateRequest);

        // 3. 조회된 사용자 정보 응답 //
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
