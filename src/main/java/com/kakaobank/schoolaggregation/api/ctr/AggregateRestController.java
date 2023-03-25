package com.kakaobank.schoolaggregation.api.ctr;

import com.kakaobank.schoolaggregation.api.dto.request.ExtractSchoolWordRequest;
import com.kakaobank.schoolaggregation.api.dto.response.ExtractSchoolWordResponse;
import com.kakaobank.schoolaggregation.api.svc.WordExtractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@Api(tags = {" 단어 집계 서비스 컨트롤러"})
@SwaggerDefinition(tags = {
    @Tag(name = "단어 집계 서비스 컨트롤러", description = ".csv 파일안에 특정 카테고리 단어 카운트 집계 리턴 기능 제공")
})
@RequestMapping("/api/v1/aggs/words")
@RestController
public class AggregateRestController {

    @Autowired
    private WordExtractService wordExtractService;


    @ApiOperation(value = "학교 이름 추출 & 카운트", notes = ".csv 파일을 받아 학교 이름을 추출 & 카운트 하여 result.txt로 생성 후 결과값을 반환한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "API 정상 응답"),
        @ApiResponse(code = 500, message = "서버 에러")
    })
    @Parameter(hidden=true)
    @PostMapping(path = "/schools", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ExtractSchoolWordResponse> parseSchoolName(
        @ModelAttribute ExtractSchoolWordRequest request)  {

        var result = wordExtractService.schoolWordsCountExtract(request);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/test")
    public ResponseEntity<ExtractSchoolWordResponse> test()  {

        return ResponseEntity.ok(new ExtractSchoolWordResponse(true,"pathTest",1,1,1));
    }


    @Operation(summary = "get test api summary", description = "[@Operation] get test api")
    @GetMapping("/get/{id}")
    public String getTest1(
        @Parameter(name = "id", description = "아이디값", example = "예시")
        @PathVariable String id,
        @RequestParam(value = "q", defaultValue = "default", required = false) String queryParam
    ) {

        return id + queryParam;
    }


}
