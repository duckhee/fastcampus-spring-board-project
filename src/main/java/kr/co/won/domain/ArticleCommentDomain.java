package kr.co.won.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


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

    private ArticleCommentDomain(ArticleDomain article, UserDomain userAccount, Long parentCommentId, String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.content = content;
        this.parentCommentId = parentCommentId;
    }

    public static ArticleCommentDomain of(ArticleDomain article, UserDomain userAccount, String content) {
        return new ArticleCommentDomain(article, userAccount, null, content);
    }

    public void addChildComment(ArticleCommentDomain articleCommentDomain) {
        articleCommentDomain.setParentCommentId(this.getId());
        this.getChildComments().add(articleCommentDomain);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    // 단방향으로 연결하기
    @Column(updatable = false)
    private Long parentCommentId;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleDomain article; // 게시글 아이디

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserDomain userAccount; // 유저 정보 (ID)

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 댓글 본문


    @ToString.Exclude
    // cascade = CascadeType.ALL -> 부모 댓글이 삭제 시 자식 댓글도 삭제하도록 설정
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    @OrderBy("createdAt ASC")
    private Set<ArticleCommentDomain> childComments = new LinkedHashSet<>();

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
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
