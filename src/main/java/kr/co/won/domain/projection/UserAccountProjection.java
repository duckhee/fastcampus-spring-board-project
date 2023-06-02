package kr.co.won.domain.projection;


import kr.co.won.domain.UserDomain;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name ="withoutPassword", types = UserDomain.class)
public interface UserAccountProjection {
    String getUserId();
    String getEmail();
    String getNickname();
    String getMemo();
    LocalDateTime getCreatedAt();
    String getCreatedBy();
    LocalDateTime getModifiedAt();
    String getModifiedBy();
}
