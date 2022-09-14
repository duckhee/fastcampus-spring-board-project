package kr.co.won.service;

import kr.co.won.domain.ArticleDomain;
import kr.co.won.domain.UserDomain;
import kr.co.won.domain.type.SearchType;
import kr.co.won.dto.ArticleDomainDto;
import kr.co.won.dto.ArticleUpdateDto;
import kr.co.won.dto.ArticleWithCommentsDto;
import kr.co.won.dto.UserAccountDto;
import kr.co.won.repository.ArticleRepository;
import kr.co.won.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDomainDto> searchArticles(SearchType type, String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDomainDto::from);
        }

        return switch (type) {
            case TITLE -> articleRepository.findByTitleContaining(keyword, pageable).map(ArticleDomainDto::from);
            case CONTENT -> articleRepository.findByContentContaining(keyword, pageable).map(ArticleDomainDto::from);
            case ID ->
                    articleRepository.findByUserAccount_UserIdContaining(keyword, pageable).map(ArticleDomainDto::from);
            case NICKNAME ->
                    articleRepository.findByUserAccount_NickNameContaining(keyword, pageable).map(ArticleDomainDto::from);
            case HASHTAG -> articleRepository.findByHashTag("#" + keyword, pageable).map(ArticleDomainDto::from);
        };

    }

    @Transactional(readOnly = true)
    public ArticleDomainDto searchArticle(long l) {
        return articleRepository.findById(l).map(ArticleDomainDto::from).orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
    }

    public void saveArticle(ArticleDomainDto newArticle) {
        UserDomain userAccount = userRepository.getReferenceById(newArticle.userAccountDto().userId());
        articleRepository.save(newArticle.toEntity(userAccount));
    }

    public void updateArticle(Long articleId, ArticleUpdateDto updateArticle) {
        try {
            // getOne
            ArticleDomain article = articleRepository.getReferenceById(articleId);
            if (updateArticle.title() != null || !updateArticle.title().isBlank())
                article.setTitle(updateArticle.title());
            if (updateArticle.content() != null || !updateArticle.content().isBlank())
                article.setContent(updateArticle.content());
            article.setHashTag(updateArticle.hashTag());
        } catch (EntityNotFoundException exception) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다. - dto : {}", updateArticle);
        }
    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }


    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public ArticleDomainDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDomainDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }


    @Transactional(readOnly = true)

    public Page<ArticleDomainDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }
        return articleRepository.findByHashTag(hashtag, pageable).map(ArticleDomainDto::from);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }


    public List<String> getHashTags() {
        return articleRepository.findAllDistinctHashTags();
    }
}
