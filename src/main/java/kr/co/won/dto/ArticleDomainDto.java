package kr.co.won.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleDomainDto(LocalDateTime createdAt, String createdBy, String title, String content,
                               String hashTag) implements Serializable {


    public static ArticleDomainDto of(String title, String content, String hashTag, LocalDateTime createdAt, String createdBy) {
        return new ArticleDomainDto(createdAt, createdBy, title, content, hashTag);
    }
}

