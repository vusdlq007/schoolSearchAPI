package com.kakaobank.schoolaggregation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExtractLogResult {
    FAIL("FAIL", "실패"),
    SUCCESS("SUCCESS", "성공");

    private final String value;
    private final String desc;

}
