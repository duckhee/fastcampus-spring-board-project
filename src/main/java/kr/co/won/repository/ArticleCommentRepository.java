package kr.co.won.repository;

import kr.co.won.domain.ArticleCommentDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource // 자동으로 REST API 를 만들어주는 annotation
public interface ArticleCommentRepository extends JpaRepository<ArticleCommentDomain, Long> {
}
