package com.kakaobank.schoolaggregation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class ExtractLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long replyId;

    @Column(length = 20000)
    private String originReply;

    @Column(length = 20000)
    private String parseResult;

    private String csvFileName;

    @Enumerated(EnumType.STRING)
    private ExtractLogResult status;

    private LocalDateTime created;

    private ExtractLog(String originReply, String parseResult, String csvFileName, Long replyId,
        ExtractLogResult status) {
        this.setOriginReply(originReply);
        this.setParseResult(parseResult);
        this.setCsvFileName(csvFileName);
        this.setReplyId(replyId);
        this.setExtractLogStatus(status);
        this.setCreated(LocalDateTime.now());
    }

    public static ExtractLog create(String originReply, String parseResult, String csvFileName,
        Long replyId,
        ExtractLogResult status) {
        return new ExtractLog(originReply, parseResult, csvFileName, replyId, status);
    }

    private void setOriginReply(String originReply) {
        this.originReply = originReply;
    }

    private void setParseResult(String parseResult) {
        this.parseResult = parseResult;
    }

    private void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    private void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    private void setExtractLogStatus(ExtractLogResult ExtractLogStatus) {
        this.status = ExtractLogStatus;
    }

    private void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
