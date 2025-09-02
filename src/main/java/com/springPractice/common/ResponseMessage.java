package com.springPractice.common;

import org.springframework.http.HttpStatus;

// 성공 응답 메세지 별도 관리 //
// 상태코드 관리 가능
public enum ResponseMessage {
    SUCCESS_CREATE("생성 성공", HttpStatus.CREATED);

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
