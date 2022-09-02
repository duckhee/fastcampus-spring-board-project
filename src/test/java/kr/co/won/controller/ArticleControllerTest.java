package kr.co.won.controller;

import kr.co.won.config.SecurityConfiguration;
import kr.co.won.dto.ArticleDomainDto;
import kr.co.won.dto.ArticleWithCommentsDto;
import kr.co.won.dto.UserAccountDto;
import kr.co.won.service.ArticleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * controller unit test
 */
@DisplayName(value = "Board View Tests")
@WebMvcTest(controllers = {
        ArticleController.class // 이렇게 설정을 하면, 특정 controller 만 테스트가 가능하다.
})
@Import(SecurityConfiguration.class) // security 설정 파일을 추가해서 인증 관련 적용을 시키기
class ArticleControllerTest {

    private final MockMvc mockMvc;

    @MockBean // @MockBean 은 Field 주입이므로 다음과 같이 진행을 해야한다.
    private ArticleService articleService;

    public ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    //    @Disabled(value = "develop")
    @DisplayName(value = "[view][GET] article list")
    @Test
    void givenNothing_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {
        // given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        // when & Then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) // 호환되는 타입을 맞다고 해준다. options 이 맞지 않아도된다.
                .andExpect(model().attributeExists("articles"))
//                .andExpect(model().attributeExists("searchTypes"))
                .andExpect(view().name("articles/index"));


        // mockito 에서 어떤 것을 호출했는지 확인하는 것
        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
    }


    @DisplayName(value = "[VIEW][GET] article detail Tests")
    @Test
    void boardDetailViewTests() throws Exception {

        // given
        Long articleId = 1L;
        given(articleService.getArticleWithComments(articleId)).willReturn(createArticleWithCommentsDto());

        // when
        mockMvc.perform(get("/articles/{id}", articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));

        // then
        then(articleService).should().getArticleWithComments(articleId);
    }

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        ArticleWithCommentsDto dummyArticle = ArticleWithCommentsDto.of(1L, createUserDto(), Set.of(), "testTitle", "content", "hashTag", LocalDateTime.now(), "won", LocalDateTime.now(), "won");
        return dummyArticle;
    }

    private UserAccountDto createUserDto() {
        return UserAccountDto.of("user", "user", "user@co.kr", "nick", "mem");
    }

    @Disabled(value = "develop")
    @DisplayName(value = "article search Test")
    @Test
    void boardSearchTests() throws Exception {
        mockMvc.perform(get("/article/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }


    @Disabled(value = "develop")

    @DisplayName(value = "article hash tag search Tests")
    @Test
    void boardSearchHashTagTests() throws Exception {
        mockMvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashtag"));
    }

}