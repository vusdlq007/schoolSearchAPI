package com.kakaobank.schoolaggregation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public enum ResponseCode {

    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, 400),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, 401),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404),
    NOT_ALLOW(HttpStatus.METHOD_NOT_ALLOWED, 403),
    SUCCESS(HttpStatus.OK, 200),
    ERROR_CREATE_RESULT(HttpStatus.INTERNAL_SERVER_ERROR, 1501, "결과 파일 생성에 실패 했습니다.");


    @Getter
    private HttpStatus status;
    @Getter
    private int errorCode;
    @Getter
    private String message;

    ResponseCode(HttpStatus status, int code) {
        this.status = status;
        this.errorCode = code;
    }

    ResponseCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.errorCode = code;
        this.message = message;
    }

}
