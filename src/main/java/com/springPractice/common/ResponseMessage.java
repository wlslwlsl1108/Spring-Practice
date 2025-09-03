package com.springPractice.common;

import org.springframework.http.HttpStatus;

// 성공 응답 메세지 별도 관리 //
// 상태코드 관리 가능
public enum ResponseMessage {
    SUCCESS_CREATE("생성 성공", HttpStatus.CREATED),
    SUCCESS_READ("조회 성공", HttpStatus.OK),
    SUCCESS_UPDATE("수정 성공", HttpStatus.OK),
    SUCCESS_DELETE("삭제 성공", HttpStatus.OK),
    SUCCESS_LOGIN("로그인 성공", HttpStatus.OK);
    // 204 (NO_CONTENT)로 지정 시 body가 무시 되어 내용이 아예 출력 안됨

    private final String message;
    private final HttpStatus status;

    ResponseMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
