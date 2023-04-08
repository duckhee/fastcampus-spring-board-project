package kr.co.won.config;

import kr.co.won.domain.UserDomain;
import kr.co.won.dto.UserAccountDto;
import kr.co.won.repository.UserRepository;
import kr.co.won.service.UserAccountService;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@TestComponent
@Import(SecurityConfiguration.class)
public class TestSecurityConfig {

    @MockBean
    private UserRepository userAccountRepository;

    @MockBean
    UserAccountService userAccountService;

    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountService.searchUser(anyString()))
                .willReturn(Optional.of(createUserAccountDto()));
        given(userAccountService.saveUser(anyString(), anyString(), anyString(), anyString(), anyString()))
                .willReturn(createUserAccountDto());
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(createUserDomain()));
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "unoTest",
                "pw",
                "uno-test@email.com",
                "uno-test",
                "test memo"
        );
    }

    private UserDomain createUserDomain() {
        return UserDomain.of("unoTest", "pw", "uno-test@email.com", "uno-test", "test memo");
    }

}

