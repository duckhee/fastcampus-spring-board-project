package kr.co.won.repository.querydsl;

import kr.co.won.domain.ArticleDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ArticleRepositoryCustom {

    List<String> findAllDistinctHashTags();

    Page<ArticleDomain> findByHashtagNames(Collection<String> hashtagNames, Pageable pageable);
}
