package com.kakaobank.schoolaggregation.api.svc;


import com.kakaobank.schoolaggregation.api.dto.request.ExtractSchoolWordRequest;
import com.kakaobank.schoolaggregation.api.dto.response.ExtractSchoolWordResponse;

public interface WordExtractService {

    ExtractSchoolWordResponse schoolWordsCountExtract(ExtractSchoolWordRequest requestDTO);

}
