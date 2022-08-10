package kr.co.won.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    /**
     * 변경된 Security 적용 방법
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
        {
            // 모든 요청에 대해서 권한이 없어도 접근이 가능하도록 설정하겠다는 의미이다.
            authorizationManagerRequestMatcherRegistry.anyRequest().permitAll();
        })
                .formLogin(); // form Login 으로 구현을 한다는 의미이다.
        return httpSecurity.build();

    }
}
