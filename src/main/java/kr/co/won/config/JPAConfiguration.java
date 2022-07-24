package kr.co.won.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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

}
