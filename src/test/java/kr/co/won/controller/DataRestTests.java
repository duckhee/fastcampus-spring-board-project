package kr.co.won.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName(value = "Data REST TEST")
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTests {

    private final MockMvc mockMvc;

    public DataRestTests(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName(value = "[API] 게시글 리스트 조회")
    @Test
    void given_when_then() throws Exception {
        mockMvc.perform(get("/api/articleDomains"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print())
        ;
    }

    @DisplayName(value = "[API] 게시글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticle_thenReturnArticleRepository() throws Exception {
        mockMvc.perform(get("/api/articleDomains/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }

    @DisplayName(value = "[API] 게시글 -> 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleReply_thenReturnArticleRepository() throws Exception {
        mockMvc.perform(get("/api/articleDomains/1/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }


    @DisplayName(value = "[API] 게시글 -> 댓글 단건 조회")
    @Test
    void givenNothing_whenArticleDomain_thenReturnArticleRepository() throws Exception {
        mockMvc.perform(get("/api/articleCommentDomains/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

}
