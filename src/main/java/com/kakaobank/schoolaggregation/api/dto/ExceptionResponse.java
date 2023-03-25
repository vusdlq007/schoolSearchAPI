package com.kakaobank.schoolaggregation.api.dto;

import lombok.Builder;

@Builder
public record ExceptionResponse(Integer errorCode, String errorMessage) {

}
