package kr.co.won.controller;

import kr.co.won.config.SecurityConfiguration;
import kr.co.won.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(value = {TestSecurityConfig.class})
@WebMvcTest(MainController.class)
class MainControllerTest {

    private final MockMvc mockMvc;

    MainControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void givenRootPath_whenRequestingRootPage_then() throws Exception {

        // Given

        // When

        // Then
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }
}