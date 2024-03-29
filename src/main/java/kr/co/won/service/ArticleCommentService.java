package kr.co.won.service;

import kr.co.won.domain.ArticleCommentDomain;
import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.UserDomain;
import kr.co.won.dto.ArticleCommentDomainDto;
import kr.co.won.repository.ArticleCommentRepository;
import kr.co.won.repository.ArticleRepository;
import kr.co.won.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleCommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDomainDto> searchArticleComments(Long articleId) {
        return commentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDomainDto::from)
                .toList();
    }

    public void saveArticleComment(ArticleCommentDomainDto dto) {
        try {
            ArticleDomain article = articleRepository.getReferenceById(dto.articleId());
            UserDomain userAccount = userRepository.getReferenceById(dto.userAccountDto().userId());
            ArticleCommentDomain articleComment = dto.toEntity(article, userAccount);

            if (dto.parentCommentId() != null) {
                ArticleCommentDomain parentComment = commentRepository.getReferenceById(dto.parentCommentId());
                parentComment.addChildComment(articleComment);
            } else {
                commentRepository.save(articleComment);
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    public void deleteArticleComment(Long articleCommentId, String userId) {
        commentRepository.deleteByIdAndUserAccount_UserId(articleCommentId, userId);
    }
}
