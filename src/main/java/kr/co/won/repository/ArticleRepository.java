package kr.co.won.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.QArticleDomain;
import kr.co.won.domain.projection.ArticleProjection;
import kr.co.won.repository.querydsl.ArticleRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.nio.channels.FileChannel;
import java.util.List;

@Repository
@RepositoryRestResource(excerptProjection = ArticleProjection.class) // 자동으로 REST API 를 만들어주는 annotation excerptProjection 는
public interface ArticleRepository extends JpaRepository<ArticleDomain, Long>,
        QuerydslPredicateExecutor<ArticleDomain>, // 기본 검색 기능이 가능하게 해주는 것이다.
        QuerydslBinderCustomizer<QArticleDomain>, // 원하는 형태의 검색을 위해서 추가하는 것이다.
        ArticleRepositoryCustom {


    List<ArticleDomain> findAllByTitle(String title);

    @Override
    default void customize(QuerydslBindings bindings, QArticleDomain root) {
        // 모든 검색이 가능한 상태 현재 PredicateExecutor 를 사용을 해서 그렇다.
        // 검색에 제외를 하기 위한 함수 호출이다.
        bindings.excludeUnlistedProperties(true);
        // 검색을 허용할 properties 의 field 값을 입력을 해준다.
        bindings.including(root.title,  root.createdAt, root.createdBy);
        // 검색을 실행할 때 어떻게 동작을 할지 정해 주는 것 like 키워드 조건에 대해서 작성이 가능하다.
//        bindings.bind(root.title).first((path, value) -> path.eq(value));
//        bindings.bind(root.title).first(SimpleExpression::eq);
        // String 의 함수를 사용해서 조건을 만들어서 넘겨줘도 된다.
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like `(value)` 형태로 쿼리가 생성된다.
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like `%(value)%` 형태로 쿼리가 생성된다.
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
//        bindings.bind(root.hashTag).first(StringExpression::containsIgnoreCase);
        // TODO
        // 날짜 형태의 검색 조건을 넣을 수 있는 방법 (시분초가 다 맞아야하므로 현재 요구 사항에 정확하게 맞지 않다.)
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

    Page<ArticleDomain> findByTitleContaining(String title, Pageable pageable);

    Page<ArticleDomain> findByContentContaining(String content, Pageable pageable);

    Page<ArticleDomain> findByUserAccount_UserIdContaining(String userId, Pageable pageable);

    Page<ArticleDomain> findByUserAccount_NickNameContaining(String nickName, Pageable pageable);

//    Page<ArticleDomain> findByHashTag(String hashTag, Pageable pageable);

    void deleteByIdAndUserAccountUserId(Long articleId, String userId);

}
