package kr.co.won.dto;

import kr.co.won.domain.HashTagDomain;

import java.time.LocalDateTime;

public record HashtagDto(Long id,
                         String hashtagName,
                         LocalDateTime createdAt,
                         String createdBy,
                         LocalDateTime modifiedAt,
                         String modifiedBy) {


    public static HashtagDto of(String hashtagName) {
        return new HashtagDto(null, hashtagName, null, null, null, null);
    }

    public static HashtagDto of(Long id, String hashtagName, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new HashtagDto(id, hashtagName, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static HashtagDto from(HashTagDomain entity) {
        return new HashtagDto(
                entity.getId(),
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
