package com.kakaobank.schoolaggregation.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "파싱 요청")
public record ExtractSchoolWordRequest(
    String path,
    MultipartFile file) {

}
