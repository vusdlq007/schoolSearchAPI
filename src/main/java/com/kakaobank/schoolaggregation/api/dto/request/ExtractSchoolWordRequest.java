package com.kakaobank.schoolaggregation.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "파싱 요청")
public record ExtractSchoolWordRequest(
    @Schema(description = "결과물 저장할 path", example = "/kakaobank/agg/schools") String path,
    @Schema(description = "파싱할 파일 데이터") MultipartFile file) {

}
