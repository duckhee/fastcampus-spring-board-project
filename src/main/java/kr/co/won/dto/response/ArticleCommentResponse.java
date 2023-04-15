package kr.co.won.dto.response;


import kr.co.won.dto.ArticleCommentDomainDto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 댓글 응답 표준 포맷 
 * @param id
 * @param content
 * @param createdAt
 * @param email
 * @param nickname
 * @param userId
 * @param parentCommentId
 * @param childComments
 */
public record ArticleCommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname,
        String userId,
        Long parentCommentId,
        Set<ArticleCommentResponse> childComments
) {

    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
        // child comment 가 정렬된 상태여야하므로 비교하여 정렬을 해주기 위한 Comparator 를 사용한다.
        Comparator<ArticleCommentResponse> childCommentComparator = Comparator.comparing(ArticleCommentResponse::createdAt)
                .thenComparingLong(ArticleCommentResponse::id);
        // 비어 있는 Set 이면서 정렬된 형태가 들어갈 수 있으므로, TreeSet 을 사용한다.
        return new ArticleCommentResponse(id, content, createdAt, email, nickname, userId, null, new TreeSet<>(childCommentComparator));
    }

    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId, Long parentCommentId) {
        // child comment 가 정렬된 상태여야하므로 비교하여 정렬을 해주기 위한 Comparator 를 사용한다.
        Comparator<ArticleCommentResponse> childCommentComparator = Comparator.comparing(ArticleCommentResponse::createdAt)
                .thenComparingLong(ArticleCommentResponse::id);
        // 비어 있는 Set 이면서 정렬된 형태가 들어갈 수 있으므로, TreeSet 을 사용한다.
        return new ArticleCommentResponse(id, content, createdAt, email, nickname, userId, parentCommentId, new TreeSet<>(childCommentComparator));
    }

    public static ArticleCommentResponse from(ArticleCommentDomainDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return ArticleCommentResponse.of(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.userAccountDto().userId(),
                dto.parentCommentId()
        );
    }

    public boolean hasParentComment() {
        return parentCommentId != null;
    }

}
