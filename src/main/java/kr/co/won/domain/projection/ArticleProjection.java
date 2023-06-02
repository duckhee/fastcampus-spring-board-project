package kr.co.won.domain.projection;

import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.UserDomain;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

// 원하는 값만 가져오기 위한 Projection 을 사용하는 것 -> 이름은 여러 Projection 을 구분하기 위해서 부여한다. type 의 경우, Projection 을 사용할 Entity 의 class 를 넣어준다.
@Projection(name = "withUserAccount", types = ArticleDomain.class)
public interface ArticleProjection {

    Long getId();

    UserDomain getUserAccount();

    String getTitle();

    String getContent();

    LocalDateTime getCreatedAt();

    String getCreatedBy();

    LocalDateTime getUpdatedAt();

    String getUpdatedBy();

}
