package kr.co.won.config;

import kr.co.won.domain.UserDomain;
import kr.co.won.repository.UserRepository;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@TestComponent
@Import(SecurityConfig.class)

public class TestSecurityConfig {

    @MockBean
    private UserRepository userAccountRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserDomain.of(
                "unoTest",
                "pw",
                "uno-test@email.com",
                "uno-test",
                "test memo"
        )));
    }

}

