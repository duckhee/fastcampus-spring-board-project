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
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(name = "tbl_article", indexes = {
        @Index(columnList = "title"), // columnList 를 사용할때 데이터 베이스에 저장되는 이름을 사용해야한다.
        @Index(columnList = "hash_tag"),
        @Index(columnList = "created_at"),
        @Index(columnList = "created_by")
})
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleDomain extends AuditingFields implements Serializable  {

    protected ArticleDomain() {
    }

    public static ArticleDomain of(String title, String content, String hashTag) {
        return new ArticleDomain(title, content, hashTag);
    }

    private ArticleDomain(String title, String content, String hashTag) {
        this.title = title;
        this.content = content;
        this.hashTag = hashTag;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "title", nullable = false, length = 255)
    private String title; // 제목

    @Setter
    @Column(name = "content", nullable = false, length = 10000)
    private String content; // 내용

    @Setter
    @Column(name = "hash_tag", nullable = true)
    private String hashTag; // 해시태그

    @OrderBy(value = "id") // id 값으로 정렬
    @ToString.Exclude // ToString 제외
    @OneToMany(mappedBy = "article", cascade = {CascadeType.ALL})
    private final Set<ArticleCommentDomain> articleComments = new LinkedHashSet<>();
/*
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 생성 일시

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy; // 생성자

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 수정 일시

    @LastModifiedBy
    @Column(name = "updated_by", nullable = false, length = 100)
    private String updatedBy; // 수정자
    */

    /**
     * Article Domain Function
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleDomain articleDomain)) return false;

        return id != null && id.equals(articleDomain.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}