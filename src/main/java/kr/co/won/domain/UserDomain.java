package kr.co.won.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Getter
@ToString
@Table(name = "tbl_user", indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "created_at"),
        @Index(columnList = "created_by")
})
@Entity
public class UserDomain extends AuditingFields implements Serializable {

    @Id
    @Column(length = 50, name = "user_id")
    private String userId;

    @Setter
    @Column(nullable = false, name = "user_password")
    private String userPassword;

    @Setter
    @Column(length = 100, name = "email")
    private String email;

    @Setter
    @Column(length = 100, name = "nick_name")
    private String nickName;

    @Setter
    @Column(name = "memo")
    private String memo;

    protected UserDomain() {
    }

    private UserDomain(String userId, String userPassword, String email, String nickName, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickName = nickName;
        this.memo = memo;
    }

    public static UserDomain of(String userId, String userPassword, String email, String nickName, String memo) {
        return new UserDomain(userId, userPassword, email, nickName, memo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDomain that)) return false;
        return userId != null && userId.equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
