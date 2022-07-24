package kr.co.won.repository;

import kr.co.won.domain.ArticleDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleDomain, Long> {

}
