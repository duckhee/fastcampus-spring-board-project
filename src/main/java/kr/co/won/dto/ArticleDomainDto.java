package kr.co.won.dto;

import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.UserDomain;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleDomainDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashTag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements Serializable {


    public static ArticleDomainDto of(UserAccountDto userAccountDto, String title, String content, String hashTag) {
        return new ArticleDomainDto(null, userAccountDto, title, content, hashTag, null, null, null, null);
    }

    public static ArticleDomainDto from(ArticleDomain articleDomain) {
        return new ArticleDomainDto(
                articleDomain.getId(),
                UserAccountDto.from(articleDomain.getUserAccount()),
                articleDomain.getTitle(),
                articleDomain.getContent(),
                articleDomain.getHashTag(),
                articleDomain.getCreatedAt(),
                articleDomain.getCreatedBy(),
                articleDomain.getUpdatedAt(),
                articleDomain.getUpdatedBy()
        );
    }

    public ArticleDomain toEntity(UserDomain userDomain) {
        return ArticleDomain.of(
                userDomain,
                title,
                content,
                hashTag);
    }
}

