package kr.co.won.service;

import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.type.SearchType;
import kr.co.won.dto.ArticleDomainDto;
import kr.co.won.dto.ArticleUpdateDto;
import kr.co.won.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName(value = "비즈니스로직 - 게시글 서비스 테스트")
@ExtendWith(MockitoExtension.class) // Mockito 확장 기능을 사용을 하기 위한 설정
class ArticleServiceTest {

    @InjectMocks // 주입이 필요한 대상에 대한 설정
    private ArticleService sut;

    @Mock // 가짜 객체를 만들어줄 대상
    private ArticleRepository articleRepository;

    /**
     * 각 게시글 페이지로 이동
     * 페이지 네이션
     * 홈 버튼 -> 게시판 페이지 리다이렉션
     * 정렬 기능
     */
    @DisplayName(value = "게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnArticleListTests() {
        // Given


        // When
        Page<ArticleDomainDto> articles = sut.searchArticles(SearchType.TITLE, "search TITLE"); // 제목, 본문, ID, 닉네임, 해시태그

        // Then
        Assertions.assertThat(articles).isNotNull();
    }


    @DisplayName(value = "게시글을 조회하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnArticleTests() {
        // Given


        // When
        ArticleDomainDto articles = sut.searchArticle(1L);

        // Then
        Assertions.assertThat(articles).isNotNull();
    }

    @DisplayName(value = "게시글 정보를 입력하면, 게시글을 작성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSaveArticle() {
        // Given
        ArticleDomainDto newArticle = ArticleDomainDto.of("new Title", "new Content", "hashTag", LocalDateTime.now(), "won");

        // mockito
        // mockito 의 return 값이 void 일경우 사용을 하는 것이 willDoNoting().given()
//        willDoNothing().given(articleRepository.save(any(ArticleDomain.class)));
        given(articleRepository.save(any(ArticleDomain.class))).willReturn(null);

        // When
        sut.saveArticle(newArticle);

        // Then
        // mockito 를 통해서 실제로 호출이 되었는지 확인을 하는 코드이다.
        then(articleRepository).should().save(any(ArticleDomain.class));
    }

    @DisplayName(value = "게시글 ID 와 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleInfo_whenModifyArticle_thenUpdateArticle() {

        // Given
        ArticleUpdateDto updateArticle = ArticleUpdateDto.of("new Title", "new Content", "hashTag");
        // mockito
        given(articleRepository.save(any(ArticleDomain.class))).willReturn(null);
        // When
        sut.updateArticle(1L, updateArticle);
        // Then

        then(articleRepository).should().save(any(ArticleDomain.class));
    }

    @DisplayName(value = "게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeleteArticle_thenDeleteArticle() {
        // given
        Long articleId = 1L;

        // mockito
        willDoNothing().given(articleRepository).delete(any(ArticleDomain.class));

        // when
        sut.deleteArticle(articleId);

        // then
        then(articleRepository).should().delete(any(ArticleDomain.class));
    }
}