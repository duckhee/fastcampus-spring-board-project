package kr.co.won.domain.projection;



import kr.co.won.domain.ArticleCommentDomain;
import kr.co.won.domain.UserDomain;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "withUserAccount", types = ArticleCommentDomain.class)
public interface ArticleCommentProjection {
    Long getId();

    UserDomain getUserAccount();

    Long getParentCommentId();

    String getContent();

    LocalDateTime getCreatedAt();

    String getCreatedBy();

    LocalDateTime getModifiedAt();

    String getModifiedBy();
}
