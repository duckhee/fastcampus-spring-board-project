package kr.co.won.service;

import kr.co.won.domain.ArticleDomain;
import kr.co.won.dto.ArticleCommentDomainDto;
import kr.co.won.repository.ArticleCommentRepository;
import kr.co.won.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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

    @Disabled
    @DisplayName(value = "게시글 아이디로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchArticleComments_thenReturnArticleComments() {

        // Given
        Long articleId = 1L;

        ArticleDomain article = ArticleDomain.of(null,"title", "content", "hashTag");
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        // When
        List<ArticleCommentDomainDto> articleComments = articleCommentService.searchArticleComments(articleId);

        // Then
        Assertions.assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);

    }

    @DisplayName(value = "댓글 정보를 입력하면, 댓글을 저장한다.")
    @Test
    void givenCommentInfo_whenSaveArticleComments_thenReturn(){


    }

}