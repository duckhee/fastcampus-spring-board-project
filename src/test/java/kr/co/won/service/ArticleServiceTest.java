package kr.co.won.service;

import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.UserDomain;
import kr.co.won.domain.type.SearchType;
import kr.co.won.dto.ArticleDomainDto;
import kr.co.won.dto.ArticleUpdateDto;
import kr.co.won.dto.UserAccountDto;
import kr.co.won.repository.ArticleRepository;
import kr.co.won.repository.UserRepository;
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
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Mock
    private UserRepository userRepository;

    /**
     * 각 게시글 페이지로 이동
     * 페이지 네이션
     * 홈 버튼 -> 게시판 페이지 리다이렉션
     * 정렬 기능
     */
    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnArticleListTests() {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());
        // When
        Page<ArticleDomainDto> articles = sut.searchArticles(null, null, pageable); // 제목, 본문, ID, 닉네임, 해시태그

        // Then
        assertThat(articles).isNotNull();
        then(articleRepository).should().findAll(pageable);
    }


    @Disabled
    @DisplayName("검색어 없이 게시글을 해시태그 검색하면, 빈 페이지를 반환한다.")
    @Test
    void givenNoSearchParameters_whenSearchingArticlesViaHashtag_thenReturnsEmptyPage() {
        // Given
        Pageable pageable = Pageable.ofSize(20);

        // When
        Page<ArticleDomainDto> articles = sut.searchArticlesViaHashtag(null, pageable);

        // Then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        // 호출하지 않았는지 확인을 하는 것
        then(articleRepository).shouldHaveNoInteractions();
    }


    @Disabled
    @DisplayName("게시글을 해시태그 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenHashtag_whenSearchingArticlesViaHashtag_thenReturnsArticlesPage() {
        // Given
        String hashtag = "#java";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByHashTag(hashtag, pageable)).willReturn(Page.empty(pageable));

        // When
        Page<ArticleDomainDto> articles = sut.searchArticlesViaHashtag(hashtag, pageable);

        // Then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).should().findByHashTag(hashtag, pageable);
    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage() {
        // Given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDomainDto> articles = sut.searchArticles(searchType, searchKeyword, pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(searchKeyword, pageable);
    }


    @Disabled
    @DisplayName("해시태그를 조회하면, 유니크 해시태그 리스트를 반환한다")
    @Test
    void givenNothing_whenCalling_thenReturnsHashtags() {
        // Given
        List<String> expectedHashtags = List.of("#java", "#spring", "#boot");
        given(articleRepository.findAllDistinctHashTags()).willReturn(expectedHashtags);

        // When
        List<String> actualHashtags = sut.getHashTags();

        // Then
        assertThat(actualHashtags).isEqualTo(expectedHashtags);
        then(articleRepository).should().findAllDistinctHashTags();
    }


    @DisplayName(value = "게시글을 조회하면, 게시글 페이지를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnArticleTests() {
        // Given
        ArticleDomain article = createArticle();

        given(articleRepository.findById(any(Long.class))).willReturn(Optional.of(article));

        // When
        ArticleDomainDto articles = sut.searchArticle(1L);

        // Then
        assertThat(articles).isNotNull();
        then(articleRepository).should().findById(any(Long.class));
    }

    @Disabled
    @DisplayName(value = "게시글 정보를 입력하면, 게시글을 작성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSaveArticle() {
        // Given
        ArticleDomainDto newArticle = ArticleDomainDto.of(null, "new Title", "new Content", "hashTag");

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

    @Disabled
    @DisplayName(value = "게시글 ID 와 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleInfo_whenModifyArticle_thenUpdateArticle() {

        // Given
        ArticleDomainDto updateArticle = ArticleDomainDto.of(createUserAccountDto(), "new Title", "new Content", "hashTag");
        // mockito
        given(articleRepository.save(any(ArticleDomain.class))).willReturn(null);
        // When
        sut.updateArticle(1L, updateArticle);
        // Then

        then(articleRepository).should().save(any(ArticleDomain.class));
    }

    @Disabled
    @DisplayName(value = "게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeleteArticle_thenDeleteArticle() {
        // given
        Long articleId = 1L;

        // mockito
        willDoNothing().given(articleRepository).deleteByIdAndUserAccountUserId(articleId, "uno");

        // when
        sut.deleteArticle(articleId, "uno");

        // then
        then(articleRepository).should().delete(any(ArticleDomain.class));
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
                "content",
                "#java"
        );
//        ReflectionTestUtils.setField(article, "id", 1L);

        return article;
    }

    private ArticleDomainDto createArticleDto() {
        return createArticleDto("title", "content", "#java");
    }

    private ArticleDomainDto createArticleDto(String title, String content, String hashtag) {
        return ArticleDomainDto.of(
                1L,
                createUserAccountDto(),
                title,
                content,
                hashtag,
                LocalDateTime.now(),
                "Uno",
                LocalDateTime.now(),
                "Uno");
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
}