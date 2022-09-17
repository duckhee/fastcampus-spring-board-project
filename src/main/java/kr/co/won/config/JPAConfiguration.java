package kr.co.won.config;

import kr.co.won.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.DriverManager;
import java.util.Optional;

/**
 * user name 을 넣어주기 위한 JPA Auditing 설정
 */
@EnableJpaAuditing
@Configuration
public class JPAConfiguration {

    @Bean
    public AuditorAware<String> stringAuditorAware() {
        return () -> Optional.of("won"); // TODO Change AUth
    }

//    @Bean
//    public AuditorAware<String> auditorAware() {
//        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .map(BoardPrincipal.class::cast)
//                .map(BoardPrincipal::getUsername);
//    }


}
