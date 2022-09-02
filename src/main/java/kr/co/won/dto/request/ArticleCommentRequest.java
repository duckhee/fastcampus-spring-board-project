package kr.co.won.dto.request;


import kr.co.won.dto.ArticleCommentDomainDto;
import kr.co.won.dto.UserAccountDto;

public record ArticleCommentRequest(Long articleId, String content) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDomainDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDomainDto.of(
                articleId,
                userAccountDto,
                content
        );
    }

}
