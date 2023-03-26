package com.kakaobank.schoolaggregation.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파싱 결과 응답")
public record ExtractSchoolWordResponse( boolean status,
                                         String path,
                                         long totalParseCount,
                                         int successParseCount,
                                         int failParseCount) {

}
