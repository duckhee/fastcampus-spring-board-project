package kr.co.won.dto.request;


import kr.co.won.dto.ArticleDomainDto;
import kr.co.won.dto.UserAccountDto;

public record ArticleRequest(
        String title,
        String content,
        String hashtag
) {

    public static ArticleRequest of(String title, String content, String hashtag) {
        return new ArticleRequest(title, content, hashtag);
    }

    public ArticleDomainDto toDto(UserAccountDto userAccountDto) {
        return ArticleDomainDto.of(
                userAccountDto,
                title,
                content,
                hashtag
        );
    }

}
