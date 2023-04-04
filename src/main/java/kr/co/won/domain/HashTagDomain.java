package kr.co.won.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@Table(
        name = "tbl_hash_tag",
        indexes = {
                @Index(columnList = "hashtag_name", unique = true),
                @Index(columnList = "created_at"),
                @Index(columnList = "created_by")
        }
)
@ToString(callSuper = true) // parent class 도 추가해서 toString 을 보여준다.
public class HashTagDomain extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "hashtag_name", nullable = false)
    private String hashTagName; // 해시 태그 이름

    @ToString.Exclude // @ToString 에서 제외 시키는 것
    @ManyToMany(mappedBy = "hashtags")
    private Set<ArticleDomain> articles = new LinkedHashSet<>(); // 순서가 중요하면 LinkedHashSet 을 사용한다.

    protected HashTagDomain() {
    }

    private HashTagDomain(String hashTagName) {
        this.hashTagName = hashTagName;
    }

    public static HashTagDomain of(String hashTagName) {
        return new HashTagDomain(hashTagName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTagDomain that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
