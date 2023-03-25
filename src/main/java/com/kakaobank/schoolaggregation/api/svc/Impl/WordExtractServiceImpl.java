package com.kakaobank.schoolaggregation.api.svc.Impl;

import com.kakaobank.schoolaggregation.api.dto.request.ExtractSchoolWordRequest;
import com.kakaobank.schoolaggregation.api.dto.response.ExtractSchoolWordResponse;
import com.kakaobank.schoolaggregation.api.svc.WordExtractService;
import lombok.extern.slf4j.Slf4j;
import com.kakaobank.schoolaggregation.extractswords.ExtractSchoolNameHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
public class WordExtractServiceImpl implements WordExtractService {

    private final ExtractSchoolNameHandler extractSchoolNameHandler;

    public WordExtractServiceImpl(
        ExtractSchoolNameHandler extractSchoolNameHandler) {
        this.extractSchoolNameHandler = extractSchoolNameHandler;
    }

    @Override
    public ExtractSchoolWordResponse schoolWordsCountExtract(ExtractSchoolWordRequest requestDTO) {
        MultipartFile file = requestDTO.file();
        String path = requestDTO.path();

        ExtractSchoolWordResponse result = extractSchoolNameHandler.aggSchoolName(file,path);

        return result;
    }
}
