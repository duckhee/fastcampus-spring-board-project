package kr.co.won.repository;

import kr.co.won.config.TestJpaConfig;
import kr.co.won.domain.ArticleCommentDomain;
import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.UserDomain;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.InstanceOfAssertFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


//@Disabled
@DisplayName(value = "JPA Connect Tests")
@Import(value = {
        TestJpaConfig.class
})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
class JpaRepositoryTest {

    @PersistenceContext
    private final EntityManager entityManager;
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    private final UserRepository userRepository;

    JpaRepositoryTest(@Autowired EntityManager entityManager, @Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository, @Autowired UserRepository userRepository) {
        this.entityManager = entityManager;
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userRepository = userRepository;
    }

    @DisplayName(value = "select Tests")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        UserDomain sampleUser = UserDomain.of("test", "test", "tes", "tes", "aaa");
        UserDomain savedUser = userRepository.save(sampleUser);
        // Given
        long previousCount = articleRepository.count();
//        ArticleDomain articleDomain = ArticleDomain.of(savedUser, "new article", "article content", "#spring");
        ArticleDomain articleDomain = ArticleDomain.of(savedUser, "new article", "article content");
        // When
        ArticleDomain savedArticle = articleRepository.save(articleDomain);
        entityManager.flush();
        // When
        List<ArticleDomain> new_article = articleRepository.findAllByTitle("new article");

        List<ArticleDomain> articles = articleRepository.findAll();
        System.out.println("get size :: " + articles.size());
        // Then
        Assertions.assertThat(articles)
                .isNotNull();
//                .hasSize(123);
    }


    @DisplayName(value = "insert Tests")
    @Test
    void givenTestData_whenInsert_thenWorksFine() {
        UserDomain sampleUser = UserDomain.of("test", "test", "tes", "tes", "aaa");
        UserDomain savedUser = userRepository.save(sampleUser);
        // Given
        long previousCount = articleRepository.count();
//        ArticleDomain articleDomain = ArticleDomain.of(savedUser, "new article", "article content", "#spring");
        ArticleDomain articleDomain = ArticleDomain.of(savedUser, "new article", "article content");

        // When
        ArticleDomain savedArticle = articleRepository.save(articleDomain);
        List<ArticleDomain> articles = articleRepository.findAll();

        // Then
        Assertions.assertThat(articles)
                .isNotNull()
                .hasSize((int) (previousCount + 1L));
    }

    @DisplayName(value = "update Tests")
    @Test
    void givenTestData_whenUpdate_thenWorksFine() {
        UserDomain sampleUser = UserDomain.of("test", "test", "tes", "tes", "aaa");
        UserDomain savedUser = userRepository.save(sampleUser);
        // Given
        long previousCount = articleRepository.count();
//        ArticleDomain articleDomain = ArticleDomain.of(savedUser, "new article", "article content", "#spring");
        ArticleDomain articleDomain = ArticleDomain.of(savedUser, "new article", "article content");
        ArticleDomain savedArticle = articleRepository.saveAndFlush(articleDomain);
        // 영속성 초기화
        entityManager.flush();
        ArticleDomain findArticle = articleRepository.findById(savedArticle.getId()).orElseThrow(() -> new IllegalArgumentException());
        String updateHashCode = "#testing";

        // When
//        findArticle.setHashTag(updateHashCode);
        ArticleDomain updateArticle = articleRepository.saveAndFlush(findArticle);
        entityManager.flush();

        // Then
//        Assertions.assertThat(updateArticle).hasFieldOrPropertyWithValue("hashTag", updateHashCode);
    }

    @DisplayName(value = "delete Tests")
    @Test
    void givenTestData_whenDelete_thenWorksFine() {
        // Given
        long previousCount = articleRepository.count();
//        ArticleDomain articleDomain = ArticleDomain.of(null, "new article", "article content", "#spring");
//        ArticleDomain articleDomain = ArticleDomain.of(null, "new article", "article content");
        ArticleDomain articleDomain = articleRepository.findById(1L).orElseThrow();
        ArticleDomain savedArticle = articleRepository.saveAndFlush(articleDomain);

        ArticleDomain findArticle = articleRepository.findById(savedArticle.getId()).orElseThrow(() -> new IllegalArgumentException());
        entityManager.flush();

        // When
        articleRepository.delete(findArticle);


        // Then
        assertThrows(IllegalArgumentException.class, () -> articleRepository.findById(findArticle.getId()).orElseThrow(() -> new IllegalArgumentException()));
    }

    @DisplayName(value = "대 댓글 조회 테스트")
    @Test
    void givenParentCommentId_whenSelecting_thenReturnsChildComments() {
        // given

        // when
        Optional<ArticleCommentDomain> parentComment = articleCommentRepository.findById(1L);


        // then
        Assertions.assertThat(parentComment).get()
                .hasFieldOrPropertyWithValue("parentCommentId", null)
                .extracting("childComments", InstanceOfAssertFactories.COLLECTION)
                .hasSize(4);
    }


    @DisplayName("댓글에 대댓글 삽입 테스트")
    @Test
    void givenParentComment_whenSaving_thenInsertsChildComment() {
        // Given
        ArticleCommentDomain parentComment = articleCommentRepository.getReferenceById(1L);
        ArticleCommentDomain childComment = ArticleCommentDomain.of(
                parentComment.getArticle(),
                parentComment.getUserAccount(),
                "대댓글"
        );

        // When
        parentComment.addChildComment(childComment);
        articleCommentRepository.flush();

        // Then
        assertThat(articleCommentRepository.findById(1L)).get()
                .hasFieldOrPropertyWithValue("parentCommentId", null)
                .extracting("childComments", InstanceOfAssertFactories.COLLECTION)
                .hasSize(5);
    }

    @DisplayName("댓글 삭제와 대댓글 전체 연동 삭제 테스트")
    @Test
    void givenArticleCommentHavingChildComments_whenDeletingParentComment_thenDeletesEveryComment() {
        // Given
        ArticleCommentDomain parentComment = articleCommentRepository.getReferenceById(1L);
        long previousArticleCommentCount = articleCommentRepository.count();

        // When
        articleCommentRepository.delete(parentComment);

        // Then
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - 5); // 테스트 댓글 + 대댓글 4개
    }

    @DisplayName("댓글 삭제와 대댓글 전체 연동 삭제 테스트 - 댓글 ID + 유저 ID")
    @Test
    void givenArticleCommentIdHavingChildCommentsAndUserId_whenDeletingParentComment_thenDeletesEveryComment() {
        // Given
        long previousArticleCommentCount = articleCommentRepository.count();

        // When
        articleCommentRepository.deleteByIdAndUserAccount_UserId(1L, "uno");

        // Then
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - 5); // 테스트 댓글 + 대댓글 4개
    }

}