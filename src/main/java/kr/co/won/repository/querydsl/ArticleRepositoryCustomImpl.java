package kr.co.won.repository.querydsl;

import com.querydsl.jpa.JPQLQuery;
import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.QArticleDomain;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static kr.co.won.domain.QArticleDomain.articleDomain;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(ArticleDomain.class);
    }

    @Override
    public List<String> findAllDistinctHashTags() {
        QArticleDomain article = articleDomain;

        JPQLQuery<String> query = from(article)
                .distinct()
                .select(article.hashTag)
                .where(article.hashTag.isNotNull());
        return query.fetch();
    }
}
