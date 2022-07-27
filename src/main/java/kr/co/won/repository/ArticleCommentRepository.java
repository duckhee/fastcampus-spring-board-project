package kr.co.won.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import kr.co.won.domain.ArticleCommentDomain;
import kr.co.won.domain.QArticleCommentDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
@Repository
@RepositoryRestResource // 자동으로 REST API 를 만들어주는 annotation
public interface ArticleCommentRepository extends JpaRepository<ArticleCommentDomain, Long>,
        QuerydslPredicateExecutor<ArticleCommentDomain>,
        QuerydslBinderCustomizer<QArticleCommentDomain> {

    @Override
    default void customize(QuerydslBindings bindings, QArticleCommentDomain root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.createdAt, root.createdBy);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
