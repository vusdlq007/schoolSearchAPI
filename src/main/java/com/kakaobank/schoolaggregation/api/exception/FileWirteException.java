package com.kakaobank.schoolaggregation.api.exception;

import com.kakaobank.schoolaggregation.constant.ResponseCode;
import java.io.Serial;
import lombok.Getter;


@Getter
public class FileWirteException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8281137386061423752L;

    private final ResponseCode error;

    public FileWirteException(ResponseCode e) {
        super(e.getMessage());
        this.error = e;
    }

}
