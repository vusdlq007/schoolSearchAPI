package com.kakaobank.schoolaggregation.extractswords;

import com.kakaobank.schoolaggregation.api.dto.response.ExtractSchoolWordResponse;
import com.kakaobank.schoolaggregation.api.exception.FileWirteException;
import com.kakaobank.schoolaggregation.api.repo.ExtractLogRepository;
import com.kakaobank.schoolaggregation.constant.ResponseCode;
import com.kakaobank.schoolaggregation.entity.ExtractLogResult;
import com.kakaobank.schoolaggregation.entity.ExtractLog;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kakaobank.schoolaggregation.utils.FileUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExtractSchoolNameHandler {

    private final FileUtils fileUtil;

    private final ExtractLogRepository parseLogRepository;

    @Transactional
    public ExtractSchoolWordResponse aggSchoolName(MultipartFile file, String path) {
        //csv 파싱
        var list = fileUtil.readCsvToList(file);

        HashMap<String, Integer> countMap = new HashMap<>();
        String parseCountResult;
        StringBuilder parseLogResult = new StringBuilder();

        parseLogResult.append("------------------------------").append("\n");
        parseLogResult.append("aggregate start time : ").append(LocalDateTime.now()).append("\n");
        parseLogResult.append("csv file name : ").append(file.getOriginalFilename())
            .append("\n");
        parseLogResult.append("aggregate target count : ").append(list.size()).append("\n");
        parseLogResult.append("------------------------------").append("\n");

        log.info("------------------------------");
        log.info("aggregate start time : {}", LocalDateTime.now());
        log.info("csv file name :  {}", file.getOriginalFilename());
        log.info("aggregate target count : {}", list.size());
        log.info("------------------------------");

        int failCount = 0;
        int successCount = 0;
        long replyId = 1;

        for (String reply : list) {

            ExtractLog parseLog;
            //특수문자, 불필요단어 정제
            String pureReply = refineReply(reply);
            //정규식을 활용하여 학교이름 추출
            String schoolName = parseSchoolName(pureReply);
            //함축어 학교이름 변경 ex) 여고 -> 여자고등학교
            schoolName = modifySchoolName(schoolName).replace(" ", "");

            //파싱 성공,실패 확인을 위핸 log 저장
            ExtractLogResult stats = ExtractLogResult.SUCCESS;
            if (schoolName.isEmpty() || !verifySchoolName(schoolName)) {
                stats = ExtractLogResult.FAIL;
            }
            parseLog = ExtractLog.create(reply, schoolName, file.getOriginalFilename(), replyId,
                stats);
            parseLogRepository.save(parseLog);

            if (stats.equals(ExtractLogResult.FAIL)) {
                parseLogResult.append("aggregate fail parseLogId : ").append(parseLog.getId())
                    .append("\n");
                log.info("aggregate fail parseLogId & word : {}, {}", parseLog.getId(), schoolName);

                failCount++;
            } else {
                countMap.put(schoolName, countMap.getOrDefault(schoolName, 0) + 1);
                successCount++;
            }

            replyId++;
        }

        parseCountResult = makeResult(countMap, file.getOriginalFilename());

        parseLogResult.append("------------------------------").append("\n");
        parseLogResult.append("aggregate end time : ").append(LocalDateTime.now()).append("\n");
        parseLogResult.append("aggregate target count : ").append(list.size()).append("\n");
        parseLogResult.append("aggregate success count : ").append(successCount).append("\n");
        parseLogResult.append("aggregate fail count : ").append(failCount).append("\n");
        parseLogResult.append("------------------------------").append("\n");

        log.info("------------------------------");
        log.info("aggregate end time : {}", LocalDateTime.now());
        log.info("aggregate target count : {}", list.size());
        log.info("aggregate success count : {}", successCount);
        log.info("aggregate fail count : {}", failCount);
        log.info("------------------------------");

        try {
            fileUtil.writeLog(path, "result.txt", parseCountResult);
            fileUtil.writeLog(path, "result.log", parseLogResult.toString());
        } catch (IOException e) {
            log.error(ResponseCode.ERROR_CREATE_RESULT.getMessage());
            throw new FileWirteException(ResponseCode.ERROR_CREATE_RESULT);
        }

        return new ExtractSchoolWordResponse(true, path, replyId - 1, successCount, failCount);
    }

    private String makeResult(HashMap<String, Integer> countMap, String fileName) {
        StringBuilder parseCountResult = new StringBuilder();

        //카운트 별로 내림차순 정렬
        List<String> keySet = new ArrayList<>(countMap.keySet());
        keySet.sort((o1, o2) -> countMap.get(o2).compareTo(countMap.get(o1)));
        parseCountResult.append("csv-file name : ").append(fileName)
            .append("\n");
        for (String key : keySet) {
            parseCountResult.append(key).append("\t").append(countMap.get(key)).append("\n");
        }

        return parseCountResult.toString();

    }

    private String refineReply(String reply) {
        return reply.replaceAll("[^가-힣a-zA-Z-,. ]", " ")
            .replaceAll("(?:저희|우리|다른|진짜|제발요|하고|말고|치고)", "");
    }

    private String parseSchoolName(String reply) {
        Matcher matcher = SCHOOL_FULL_PATTERN.matcher(reply);
        String school = "";

        if (matcher.find()) {
            return matcher.group("school");
        }

        matcher = SCHOOL_SHORT_PATTERN.matcher(reply);

        if (matcher.find()) {
            return matcher.group("school");
        }

        return school;
    }

    private String modifySchoolName(String schoolName) {
        if (schoolName.endsWith("여대")) {
            return schoolName.replace("여대", "여자대학교");
        }
        if (schoolName.endsWith("여고")) {
            return schoolName.replace("여고", "여자고등학교");
        }
        if (schoolName.endsWith("남고")) {
            return schoolName.replace("남고", "남자고등학교");
        }
        if (schoolName.endsWith("체고")) {
            return schoolName.replace("체고", "체육고등학교");
        }
        if (schoolName.endsWith("여중")) {
            return schoolName.replace("여중", "여자중학교");
        }
        if (schoolName.endsWith("남중")) {
            return schoolName.replace("남중", "남자중학교");
        }
        if (schoolName.endsWith("대학")) {
            return schoolName.replace("대학", "대학교");
        }
        if (schoolName.endsWith("대")) {
            return schoolName.replace("대", "대학교");
        }
        if (schoolName.endsWith("고")) {
            return schoolName.replace("고", "고등학교");
        }
        if (schoolName.endsWith("중")) {
            return schoolName.replace("중", "중학교");
        }
        if (schoolName.endsWith("초")) {
            return schoolName.replace("초", "초등학교");
        }
        return schoolName;
    }

    private boolean verifySchoolName(String school) {
        int length = school.replaceAll(SCHOOL_NAME_PATTERN, "").length();
        return length > 1 && length < 20;
    }


    private static final Pattern SCHOOL_FULL_PATTERN = Pattern.compile(
        "(?<school>"
            + "[가-힣 ]?[가-힣]*국제학교|"
            + "[가-힣 ]?[가-힣]*(?:초등학교|초 등학교|초등 학교|초등학 교|초 등 학교|초 등 학 교)|"
            + "[가-힣 ]?[가-힣]*(?: 남자 중학교|남자중학교|남자중 학교|남자중 학 교|남자중학 교)|"
            + "[가-힣 ]?[가-힣]*(?: 여자 중학교|여자중학교| 여자중학교| 여자중 학교|여자중 학교|여자중 학 교|여자중학 교)|"
            + "[가-힣 ]?[가-힣]*(?: 중학교|중학교|중 학교|중 학 교|중학 교)|"
            + "[가-힣 ]?[가-힣]*(?: 남자고등학교|남자고등학교|남자고 등학교|남자고등 학교|남자고등학 교|남자고 등 학교|남자고 등 학 교)|"
            + "[가-힣 ]?[가-힣]*(?: 여자[a-zA-Z]*고등학교| 여자고등학교|여자고등학교|여자고 등학교|여자고등 학교|여자고등학 교|여자고 등 학교|여자고 등 학 교| 서여자고등학교| 여자 고등학교| 동여자중학교| 여자상업고등학교)|"
            + "[가-힣 ]?[가-힣]*(?: 고등학교|고등학교|고 등학교|고등 학교|고등학 교|고 등 학교|고 등 학 교|체고| 관광고등학교)|"
            + "[가-힣 ]?[가-힣]*(?: 대학교|대학교|대 학교|대 학 교|대학 교)"
            + ")");

    private static final Pattern SCHOOL_SHORT_PATTERN = Pattern.compile(
        "(?<school>"
            + "[가-힣]+초|"
            + "[가-힣]+중|"
            + "[가-힣]+고|"
            + "[가-힣]+대"
            + ")");

    private static final String SCHOOL_NAME_PATTERN = "(?:여자[a-zA-Z]*고등학교|여자고등학교|남자고등학교|체육고등학교|고등학교|초등학교|여자중학교|남자중학교|중학교|대학교|여자대학교)";


}
