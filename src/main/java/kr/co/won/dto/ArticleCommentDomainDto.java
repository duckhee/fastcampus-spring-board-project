package kr.co.won.dto;

import kr.co.won.domain.ArticleCommentDomain;
import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.UserDomain;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleCommentDomainDto(
        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        Long parentCommentId,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static ArticleCommentDomainDto of(Long articleId, UserAccountDto userAccountDto, String content) {
        return ArticleCommentDomainDto.of(articleId, userAccountDto, null, content);
    }

    public static ArticleCommentDomainDto of(Long articleId, UserAccountDto userAccountDto, Long parentCommentId, String content) {
        return ArticleCommentDomainDto.of(null, articleId, userAccountDto, parentCommentId, content, null, null, null, null);
    }

    public static ArticleCommentDomainDto of(Long id, Long articleId, UserAccountDto userAccountDto, Long parentCommentId, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleCommentDomainDto(id, articleId, userAccountDto, parentCommentId, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleCommentDomainDto from(ArticleCommentDomain entity) {
        return new ArticleCommentDomainDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getParentCommentId(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy()
        );
    }

    public ArticleCommentDomain toEntity(ArticleDomain article, UserDomain userAccount) {
        return ArticleCommentDomain.of(
                article,
                userAccount,
                content
        );
    }

}
