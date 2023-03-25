package com.kakaobank.schoolaggregation.api.exception;

import com.kakaobank.schoolaggregation.api.dto.ExceptionResponse;
import com.kakaobank.schoolaggregation.constant.ResponseCode;
import java.nio.file.AccessDeniedException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exceptionHandler(final FileWirteException e) {
        return ResponseEntity
            .status(e.getError().getErrorCode())
            .body(new ExceptionResponse(e.getError().getErrorCode(),e.getError().getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exceptionHandler(final RuntimeException e) {
        return ResponseEntity
            .status(ResponseCode.RUNTIME_EXCEPTION.getStatus())
            .body(new ExceptionResponse(ResponseCode.RUNTIME_EXCEPTION.getErrorCode(), ResponseCode.RUNTIME_EXCEPTION.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exceptionHandler(final AccessDeniedException e) {
        return ResponseEntity
            .status(ResponseCode.ACCESS_DENIED_EXCEPTION.getStatus())
            .body(new ExceptionResponse(ResponseCode.ACCESS_DENIED_EXCEPTION.getErrorCode(),ResponseCode.ACCESS_DENIED_EXCEPTION.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exceptionHandler(final DataAccessException e) {
        return ResponseEntity
            .status(ResponseCode.ACCESS_DENIED_EXCEPTION.getStatus())
            .body(new ExceptionResponse(ResponseCode.ACCESS_DENIED_EXCEPTION.getErrorCode(),ResponseCode.ACCESS_DENIED_EXCEPTION.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exceptionHandler(final Exception e) {
        return ResponseEntity
            .status(ResponseCode.INTERNAL_SERVER_ERROR.getStatus())
            .body(new ExceptionResponse(ResponseCode.INTERNAL_SERVER_ERROR.getErrorCode(),ResponseCode.INTERNAL_SERVER_ERROR.getMessage()));
    }

}
