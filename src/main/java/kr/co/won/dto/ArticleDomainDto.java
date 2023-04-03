package kr.co.won.dto;

import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.UserDomain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleDomainDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        Set<HashtagDto> hashtagDtos,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements Serializable {


    public static ArticleDomainDto of(UserAccountDto userAccountDto, String title, String content) {
        return new ArticleDomainDto(null, userAccountDto, title, content, null, null, null, null, null);
    }

    public static ArticleDomainDto of(UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashTag) {
        return new ArticleDomainDto(null, userAccountDto, title, content, hashTag, null, null, null, null);
    }


    public static ArticleDomainDto of(Long idx, UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashTag, LocalDateTime createdAt, String createBy, LocalDateTime updatedAt, String updateBy) {
        return new ArticleDomainDto(idx, userAccountDto, title, content, hashTag, createdAt, createBy, updatedAt, updateBy);
    }

    public static ArticleDomainDto from(ArticleDomain articleDomain) {
        return new ArticleDomainDto(
                articleDomain.getId(),
                UserAccountDto.from(articleDomain.getUserAccount()),
                articleDomain.getTitle(),
                articleDomain.getContent(),
                articleDomain.getHashtags().stream().map(HashtagDto::from).collect(Collectors.toUnmodifiableSet()),
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
                content);
    }
}

