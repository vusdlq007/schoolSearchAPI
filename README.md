# schoolSearchAPI
### 기능 명세 

* [기본 : 유효 학교별 문장 Count API]

### 각 API 프로젝트 공통 스펙
- IDE: IntelliJ IDEA
- 언어 / 버전 : java / 17
- 프레임워크 : Spring boot 3.0.5
- 빌드 : Gradle
- RDBMS : H2
- Swagger 3.0 적용

## API 명세서 내용

1.**학교 단어 집계 서비스 호출 API**
  - 댓글.csv 파일
  - 집계 결과값 파일 위치


    URL : **POST** http://localhost:9803/api/v1/aggs/words/schools

**[요청]**
   <pre class='prettyprint'>
   Body Param
      "file" : 댓글.csv,
      "path" : C:\Users\김윤권\project\result
</pre>
**[응답]**
   <pre class='prettyprint'>
   {
    "status": true,
    "path": "C:\\Users\\김윤권\\project\\result",
    "totalParseCount": 1000,
    "successParseCount": 981,
    "failParseCount": 19
   }
</pre>

* * *
####[첨부 파일 목록 및 설명]
