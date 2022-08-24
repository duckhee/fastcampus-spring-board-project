package kr.co.won.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleCommentDomainDto(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt,
                                      String updatedBy, String content) implements Serializable {

    public static ArticleCommentDomainDto of(LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, String content) {
        return new ArticleCommentDomainDto(createdAt, createdBy, updatedAt, updatedBy, content);

    }
}
