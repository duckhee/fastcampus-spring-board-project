package kr.co.won.repository.querydsl;

import com.querydsl.jpa.JPQLQuery;
import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.QArticleDomain;
import kr.co.won.domain.QHashTagDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Collection;
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
                .select(article.hashtags.any().hashTagName);
        return query.fetch();
//        return null;
    }

    @Override
    public Page<ArticleDomain> findByHashtagNames(Collection<String> hashtagNames, Pageable pageable) {
        QHashTagDomain hashtag = QHashTagDomain.hashTagDomain;
        QArticleDomain article = articleDomain;
        JPQLQuery<ArticleDomain> query = from(article)
                .innerJoin(article.hashtags, hashtag)
                .where(hashtag.hashTagName.in(hashtagNames));
        List<ArticleDomain> result = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(result, pageable, query.fetchCount());
    }
}
