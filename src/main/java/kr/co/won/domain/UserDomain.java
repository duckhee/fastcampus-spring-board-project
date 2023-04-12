package kr.co.won.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Getter
@ToString(callSuper = true)
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

    private UserDomain(String userId, String userPassword, String email, String nickName, String memo, String createdBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickName = nickName;
        this.memo = memo;
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
    }

    public static UserDomain of(String userId, String userPassword, String email, String nickName, String memo) {
        return new UserDomain(userId, userPassword, email, nickName, memo);
    }

    public static UserDomain of(String userId, String userPassword, String email, String nickName, String memo, String createdBy) {
        return new UserDomain(userId, userPassword, email, nickName, memo, createdBy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDomain that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }
}
