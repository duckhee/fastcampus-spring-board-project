package kr.co.won.dto;


import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.HashTagDomain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ArticleWithCommentsDto {
    private final Long id;
    private final UserAccountDto userAccountDto;
    private final Set<ArticleCommentDomainDto> articleCommentDtos;
    private final String title;
    private final String content;
    private final Set<HashtagDto> hashtags;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public ArticleWithCommentsDto(
            Long id,
            UserAccountDto userAccountDto,
            Set<ArticleCommentDomainDto> articleCommentDtos,
            String title,
            String content,
            Set<HashtagDto> hashtags,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ) {
        this.id = id;
        this.userAccountDto = userAccountDto;
        this.articleCommentDtos = articleCommentDtos;
        this.title = title;
        this.content = content;
        this.hashtags = hashtags;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public static ArticleWithCommentsDto of(Long id, UserAccountDto userAccountDto, Set<ArticleCommentDomainDto> articleCommentDtos, String title, String content, Set<HashtagDto> hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleWithCommentsDto(id, userAccountDto, articleCommentDtos, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleWithCommentsDto from(ArticleDomain entity) {
        return new ArticleWithCommentsDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getArticleComments().stream()
                        .map(ArticleCommentDomainDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtags().stream().map(HashtagDto::from).collect(Collectors.toUnmodifiableSet()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy()
        );
    }

    public Long id() {
        return id;
    }

    public UserAccountDto userAccountDto() {
        return userAccountDto;
    }

    public Set<ArticleCommentDomainDto> articleCommentDtos() {
        return articleCommentDtos;
    }

    public String title() {
        return title;
    }

    public String content() {
        return content;
    }

    public Set<HashtagDto> hashtags() {
        return hashtags;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public String createdBy() {
        return createdBy;
    }

    public LocalDateTime modifiedAt() {
        return modifiedAt;
    }

    public String modifiedBy() {
        return modifiedBy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ArticleWithCommentsDto) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.userAccountDto, that.userAccountDto) &&
                Objects.equals(this.articleCommentDtos, that.articleCommentDtos) &&
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.content, that.content) &&
//                Objects.equals(this.hashtag, that.hashtag) &&
                Objects.equals(this.createdAt, that.createdAt) &&
                Objects.equals(this.createdBy, that.createdBy) &&
                Objects.equals(this.modifiedAt, that.modifiedAt) &&
                Objects.equals(this.modifiedBy, that.modifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userAccountDto, articleCommentDtos, title, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    @Override
    public String toString() {
        return "ArticleWithCommentsDto[" +
                "id=" + id + ", " +
                "userAccountDto=" + userAccountDto + ", " +
                "articleCommentDtos=" + articleCommentDtos + ", " +
                "title=" + title + ", " +
                "content=" + content + ", " +
//                "hashtag=" + hashtag + ", " +
                "createdAt=" + createdAt + ", " +
                "createdBy=" + createdBy + ", " +
                "modifiedAt=" + modifiedAt + ", " +
                "modifiedBy=" + modifiedBy + ']';
    }


}
