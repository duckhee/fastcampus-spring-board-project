package kr.co.won.dto.request;


import kr.co.won.domain.ArticleCommentDomain;
import kr.co.won.dto.ArticleCommentDomainDto;
import kr.co.won.dto.UserAccountDto;

public record ArticleCommentRequest(
        Long articleId,
        Long parentCommentId,
        String content
) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return ArticleCommentRequest.of(articleId, null, content);
    }

    public static ArticleCommentRequest of(Long articleId, Long parentCommentId, String content) {
        return new ArticleCommentRequest(articleId, parentCommentId, content);
    }

    public ArticleCommentDomainDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDomainDto.of(
                articleId,
                userAccountDto,
                parentCommentId,
                content
        );
    }

}