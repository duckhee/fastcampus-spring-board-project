package kr.co.won.repository;

import kr.co.won.domain.ArticleCommentDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleCommentDomain, Long> {
}
