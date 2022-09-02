package kr.co.won.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@ToString(callSuper = true)
@Table(name = "tbl_article_comment", indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "created_at"),
        @Index(columnList = "created_by")
})
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleCommentDomain extends AuditingFields implements Serializable {

    protected ArticleCommentDomain() {
    }

    private ArticleCommentDomain(UserDomain user, ArticleDomain article, String content) {
        this.article = article;
        this.content = content;
        this.user = user;
    }

    public static ArticleCommentDomain of(UserDomain user, ArticleDomain article, String content) {
        return new ArticleCommentDomain(user, article, content);
    }

    private ArticleCommentDomain(ArticleDomain article, UserDomain userAccount, String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.content = content;
    }

    public static ArticleCommentDomain of(ArticleDomain article, UserDomain userAccount, String content) {
        return new ArticleCommentDomain(article, userAccount, content);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private UserDomain user;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleDomain article; // 게시글 아이디

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private UserDomain userAccount; // 유저 정보 (ID)

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 댓글 본문

    /*
        @CreatedDate
        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt; // 생성 일자

        @CreatedBy
        @Column(name = "created_by", nullable = false, length = 100)
        private String createdBy; // 생성한 사람

        @LastModifiedDate
        @Column(nullable = false)
        private LocalDateTime updatedAt; // 수정 일자

        @LastModifiedBy
        @Column(nullable = false, length = 100)
        private String updatedBy; // 수정한 사람
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleCommentDomain that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
