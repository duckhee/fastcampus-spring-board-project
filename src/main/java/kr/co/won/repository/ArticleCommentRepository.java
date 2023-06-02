package kr.co.won.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import kr.co.won.domain.ArticleCommentDomain;
import kr.co.won.domain.QArticleCommentDomain;
import kr.co.won.domain.projection.ArticleCommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(excerptProjection = ArticleCommentProjection.class) // 자동으로 REST API 를 만들어주는 annotation excerptProjection 는 Hal API 의 embedded 할 경우 노출 시켜줄 값을 정의 해주는 것이다.
public interface ArticleCommentRepository extends JpaRepository<ArticleCommentDomain, Long>,
        QuerydslPredicateExecutor<ArticleCommentDomain>,
        QuerydslBinderCustomizer<QArticleCommentDomain> {

    List<ArticleCommentDomain> findByArticle_Id(Long articleId);


    void deleteByIdAndUserAccount_UserId(Long id, String userId);


    @Override
    default void customize(QuerydslBindings bindings, QArticleCommentDomain root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.createdAt, root.createdBy);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
