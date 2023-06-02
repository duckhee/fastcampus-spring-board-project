package kr.co.won.config;

import kr.co.won.domain.ArticleCommentDomain;
import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.HashTagDomain;
import kr.co.won.domain.UserDomain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class DataRestConfiguration {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        // hal restRepository 에서 ID의 값을 다룰 수 있게 노출 시켜주는 설정을 진행을 하는 Bean
        return RepositoryRestConfigurer.withConfig((repositoryRestConfiguration, corsRegistry) ->
                repositoryRestConfiguration.exposeIdsFor(UserDomain.class)
                        .exposeIdsFor(ArticleDomain.class)
                        .exposeIdsFor(ArticleCommentDomain.class)
                        .exposeIdsFor(HashTagDomain.class));
    }
}
