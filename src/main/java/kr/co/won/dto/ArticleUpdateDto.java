package kr.co.won.dto;

import java.io.Serializable;

public record ArticleUpdateDto(

        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashTag
) implements Serializable {

    public static ArticleUpdateDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashTag) {
        return new ArticleUpdateDto(id, userAccountDto, title, content, hashTag);
    }

}
