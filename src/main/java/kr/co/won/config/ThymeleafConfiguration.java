package kr.co.won.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    @Bean
    public SpringResourceTemplateResolver templateResolver(SpringResourceTemplateResolver defaultResolver, Thymeleaf3Properties thymeleaf3Properties) {
        defaultResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());
        return defaultResolver;
    }

    @ConstructorBinding // 생성자로 값을 입력해서 불변 객체로 이용하기 위해서 생성자 바인딩을 해준다.
    @ConfigurationProperties("spring.thymeleaf3")
    public static class Thymeleaf3Properties {
        /**
         * Use Thymeleaf 3 Decoupled Logic setting
         */
        private final boolean decoupledLogic;

        public Thymeleaf3Properties(boolean decoupledLogic) {
            this.decoupledLogic = decoupledLogic;
        }

        public boolean isDecoupledLogic() {
            return decoupledLogic;
        }
    }
}
