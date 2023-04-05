package kr.co.won.service;

import kr.co.won.domain.ArticleCommentDomain;
import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.HashTagDomain;
import kr.co.won.domain.UserDomain;
import kr.co.won.dto.ArticleCommentDomainDto;
import kr.co.won.dto.UserAccountDto;
import kr.co.won.repository.ArticleCommentRepository;
import kr.co.won.repository.ArticleRepository;
import kr.co.won.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName(value = "비즈니스 로직 - 게시글 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService articleCommentService;

    @Mock
    private ArticleCommentRepository commentRepository;
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userAccountRepository;


    @DisplayName(value = "게시글 아이디로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchArticleComments_thenReturnArticleComments() {
        // Given
        Long articleId = 1L;
        ArticleCommentDomain expectedParentComment = createArticleComment(1L, "parent content");
        ArticleCommentDomain expectedChildComment = createArticleComment(2L, "child content");
        expectedChildComment.setParentCommentId(expectedParentComment.getId());
        given(commentRepository.findByArticle_Id(articleId)).willReturn(List.of(
                expectedParentComment,
                expectedChildComment
        ));

        // When
        List<ArticleCommentDomainDto> actual = articleCommentService.searchArticleComments(articleId);

        // Then
        assertThat(actual).hasSize(2);
        assertThat(actual)
                .extracting("id", "articleId", "parentCommentId", "content")
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L, null, "parent content"),
                        tuple(2L, 1L, 1L, "child content")
                );
        then(commentRepository).should().findByArticle_Id(articleId);
    }

    @DisplayName(value = "댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenCommentInfo_whenSaveArticleComments_thenReturn() {


    }


    private ArticleCommentDomainDto createArticleCommentDto(String content) {
        return createArticleCommentDto(null, content);
    }

    private ArticleCommentDomainDto createArticleCommentDto(Long parentCommentId, String content) {
        return createArticleCommentDto(1L, parentCommentId, content);
    }

    private ArticleCommentDomainDto createArticleCommentDto(Long id, Long parentCommentId, String content) {
        return ArticleCommentDomainDto.of(
                id,
                1L,
                createUserAccountDto(),
                parentCommentId,
                content,
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "uno",
                "password",
                "uno@mail.com",
                "Uno",
                "This is memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private ArticleCommentDomain createArticleComment(Long id, String content) {
        ArticleCommentDomain articleComment = ArticleCommentDomain.of(
                createArticle(),
                createUserAccount(),
                content
        );
        ReflectionTestUtils.setField(articleComment, "id", id);

        return articleComment;
    }

    private UserDomain createUserAccount() {
        return UserDomain.of(
                "uno",
                "password",
                "uno@email.com",
                "Uno",
                null
        );
    }

    private ArticleDomain createArticle() {
        ArticleDomain article = ArticleDomain.of(
                createUserAccount(),
                "title",
                "content"
        );
        ReflectionTestUtils.setField(article, "id", 1L);
        article.addHashtag(Set.of(createHashtag(article)));

        return article;
    }

    private HashTagDomain createHashtag(ArticleDomain article) {
        return HashTagDomain.of("java");
    }


}