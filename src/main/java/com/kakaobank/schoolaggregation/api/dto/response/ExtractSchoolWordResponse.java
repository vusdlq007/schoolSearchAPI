package com.kakaobank.schoolaggregation.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파싱 결과 응답")
public record ExtractSchoolWordResponse(@Schema(description = "파싱 성공여부") boolean status,
                                        @Schema(description = "결과물 저장한 위치") String path,
                                        @Schema(description = "파싱 시도 건수") long totalParseCount,
                                        @Schema(description = "파싱 성공 건수") int successParseCount,
                                        @Schema(description = "파싱 실패 건수") int failParseCount) {

}
