package kr.co.won.dto;

import java.io.Serializable;

public record ArticleUpdateDto(
        String title,
        String content,
        String hashTag
) implements Serializable {

    public static ArticleUpdateDto of(String title, String content, String hashTag) {
        return new ArticleUpdateDto(title, content, hashTag);
    }

}