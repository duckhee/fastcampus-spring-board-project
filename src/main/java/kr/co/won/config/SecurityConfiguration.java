package kr.co.won.config;

import kr.co.won.dto.UserAccountDto;
import kr.co.won.dto.security.BoardPrincipal;
import kr.co.won.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                    // authorizationManagerRequestMatcherRegistry.anyRequest().permitAll();
                    // 특정 요청에 대해서 처리하기 위한 방법 - spring 에서 사용하는 패턴 방법을 사용해서 할 수 있는 것이 mvcMaters 이다.
                    // GET 에 대한 요청만 설정하는 것이다.
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .mvcMatchers(HttpMethod.GET, "/", "/articles", "articles/search-hashtag")
                            .permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin() // form Login 으로 구현을 한다는 의미이다.
                .and()
                .logout()
                .logoutSuccessUrl("/");
        return httpSecurity.build();

    }

    /**
     * WebSecurity Resource Setting
     */
    /*
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findById(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다. - username : " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
