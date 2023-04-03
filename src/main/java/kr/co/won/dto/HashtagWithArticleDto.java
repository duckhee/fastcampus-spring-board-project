package kr.co.won.dto;

import kr.co.won.domain.HashTagDomain;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record HashtagWithArticleDto(Long id,
                                    Set<ArticleDomainDto> articles,
                                    String hashtagName,
                                    LocalDateTime createdAt,
                                    String createdBy,
                                    LocalDateTime modifiedAt,
                                    String modifiedBy
) {

    public static HashtagWithArticleDto of(Set<ArticleDomainDto> articles, String hashtagName) {
        return new HashtagWithArticleDto(null, articles, hashtagName, null, null, null, null);
    }

    public static HashtagWithArticleDto of(Long id, Set<ArticleDomainDto> articles, String hashtagName, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new HashtagWithArticleDto(id, articles, hashtagName, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static HashtagWithArticleDto from(HashTagDomain entity) {
        return new HashtagWithArticleDto(
                entity.getId(),
                entity.getArticles().stream()
                        .map(ArticleDomainDto::from)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                entity.getHashTagName(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy()
        );
    }

    public HashtagDto toEntity() {
        return HashtagDto.of(hashtagName);
    }
}