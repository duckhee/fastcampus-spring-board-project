package kr.co.won.controller;

import kr.co.won.config.SecurityConfiguration;
import kr.co.won.config.TestSecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(value = "View Controller - auth Tests")
@WebMvcTest(Void.class)
@Import(TestSecurityConfig.class)
public class AuthControllerTests {

    private final MockMvc mockMvc;

    @Mock
    private UserDetailsService userDetailsService;


    public AuthControllerTests(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Disabled
    @DisplayName(value = "[view] [GET] - Login Page Tests")
    @Test
    void giveNothing_whenTryingToLogIn_theReturnLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}
