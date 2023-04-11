package com.kakaobank.schoolaggregation.extractswords;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


import com.kakaobank.schoolaggregation.api.repo.ExtractLogRepository;
import com.kakaobank.schoolaggregation.entity.ExtractLog;
import com.kakaobank.schoolaggregation.utils.FileUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class ExtractSchoolNameHandlerTest {
    @Mock
    private FileUtils fileUtils;
    @Mock
    private ExtractLogRepository extractLogRepository;
    @InjectMocks
    private ExtractSchoolNameHandler extractSchoolNameHandler;
    @Captor
    ArgumentCaptor<ExtractLog> logArgumentCaptor;


    @Test
    @DisplayName("댓글 파싱 테스트")
    void parseSchoolName_Success() throws IOException {

        //given
        List<String> list = new ArrayList<>();
        list.add("?경북 경산, 하양여자중학교?  이 글 보시는 하양여중 학생 분들, 공감되시는 분은 이 글 복사해서 배달의민족 "
            + "게시물에 붙여넣기 해 주세요!!  먼저 신고 좀 하지 말아주세요! 까칠하고 배고픈 중학생 입니다. "
            + "저흰 잘못 안 했어요.  안녕하세요 배달의 민족님 구구절절 저희 반 사연부터 말씀드릴게요 저희 반 선생님 "
            + "께서는 짜장면을 사 주신다고 몇 달 전부터 말씀 하셨습니다. 그런데 그 몇 달이 6개월이 지났네요... 그래도"
            + " 선생님은 벼르지도 않으십니다.. 저희 반 마른 아이들은 하루 하루가 지날 수록 말라가고 빈약해져 갑니다."
            + " 어떻게 생각하시나요? 한창 많이 먹고 크게 자라나야 할 아이들이 먹고싶은 거 하나 못 먹고 이렇게 굶주린"
            + " 학생들이 되어갑니다...... 우리는 짜장면이 먹고싶습니다. 그 검은 면발에 고소하고 짭조름한 그 양념을,"
            + " 기름기는 있지만 목넘김이 스무스한 그런. 짜장면이 먹고싶습니다. 어젠 급식 스파게티에서 노랑 검정 콜라보"
            + " 애벌레가 나왔구요. 학교가 산 꼭대기고 걸어서 가지도 못 해서 짜장면 먹기도 힘듭니다. 저흴 위해 항상"
            + " 고생하시는 선생님들을 위해. 저희 학교는 전교생 1000명도 안 되는 조그만 학교에 많은 재능을 기부 해 주시는"
            + " 선생님들을 위해서라도 짜장면 1000그릇 꼭 먹고 싶습니다.  저와 함께 하는 동지 "
            + "입니숙희숙민경민다연다지민지지영지손예진예진예진 @정다연 @백민지 모두가 마르고 굶주린 가엾은 아이들입니다."
            + "  저희 학교 전체가 짜장면을 갈구 합니다.  그리고 욕 좀 하지 마삼  사랑합니다. 배달의 민족 ❤");
        list.add("\"동탄 석우중학교요 왜냐하면 급식이 노맛이예요 그래서 맛있는자장면먹고싶어요 그리고 자장면원래조아하는데"
            + " 맨날 학교에서 나오는 자장면 진짜 무슨 3분자장면먹는것같아서 다버렸는데 이거먹고싶어요 맛이쓸것같아요\" ");
        list.add("\"저희 급식 수준인데...주세요...짜장면..❣️ 경북 영천/영천여자고등학교 짜장면이 고파요 저희를 위해"
            + " 하나의 짜장면이 필요합니다.. 저희를 보아주세요, 저희를 도와주세요, 저희를 구출해주세요.. 부탁드립니다.."
            + " 김예림김예진김수민윤유정김명주신연홍강한아정다현장다영박다인\" ");

        final String fileName = "testFile.csv";
        final String contentType = ".csv";
        final String filePath = "/test";

        MockMultipartFile file = new MockMultipartFile(
            "test",
            fileName,
            contentType,
            "test".getBytes(StandardCharsets.UTF_8)
        );
        //여기서 file에 list를 담음. fileUtils.readCsvToList를 통해 doReturn으로 list를 인자로 수행했다고 가정. 그래서 list가 readCsvToList로 가서 수행되었다고 생각.
        doReturn(list).when(fileUtils).readCsvToList(file);

        //when
        // 실제로 file을 넘기는게 아니라 위에서 mock으로 선언한 fileUtiles의 결과 값으로셋팅된 list가 들어감.
        extractSchoolNameHandler.aggSchoolName(file, filePath);
        // 행위테스트, save가 3번호출이 되었는지. save 호출간 넘겨받은 인자를 캡쳐해서 꺼내기. (aggSchoolName안에서 3번 호출됨)
        verify(extractLogRepository, Mockito.times(3)).save(logArgumentCaptor.capture());
        List<ExtractLog> logs = logArgumentCaptor.getAllValues();

        //then
        assertEquals("하양여자중학교", logs.get(0).getParseResult());
        assertEquals("석우중학교", logs.get(1).getParseResult());
        assertEquals("영천여자고등학교", logs.get(2).getParseResult());


    }
}
