package kr.co.won.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Entity
@Table(name = "tbl_article", indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashTag"),
        @Index(columnList = "careatedAt"),
        @Index(columnList = "createdBy")
})
public class ArticleDomain implements Serializable {

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
    @Column(nullable = false, length = 255)
    private String title; // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 내용

    @Setter
    @Column(nullable = true)
    private String hashTag; // 해시태그

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성 일시

    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy; // 생성자

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt; // 수정 일시

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String updatedBy; // 수정자

    /** Article Domain Function */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleDomain articleDomain)) return false;

        return id != null &&  id.equals(articleDomain.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
