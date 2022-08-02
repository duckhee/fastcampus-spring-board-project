package kr.co.won.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * controller unit test
 */
@DisplayName(value = "Board View Tests")
@WebMvcTest(controllers = {
        ArticleController.class // 이렇게 설정을 하면, 특정 controller 만 테스트가 가능하다.
})
class ArticleControllerTest {

    private final MockMvc mockMvc;


    public ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Disabled(value = "develop")
    @DisplayName(value = "[view][GET] article list")
    @Test
    void givenNothing_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {
        // given

        // when & Then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"))
                .andExpect(view().name("articles/index "));
    }

    @Disabled(value = "develop")
    @DisplayName(value = "article detail Tests")
    @Test
    void boardDetailViewTests() throws Exception {
        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));
    }

    @Disabled(value = "develop")
    @DisplayName(value = "article search Test")
    @Test
    void boardSearchTests() throws Exception {
        mockMvc.perform(get("/article/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search"))
                .andExpect(content().contentType(MediaType.TEXT_HTML));
    }


    @Disabled(value = "develop")

    @DisplayName(value = "article hash tag search Tests")
    @Test
    void boardSearchHashTagTests() throws Exception {
        mockMvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashtag"));
    }

}