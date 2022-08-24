package kr.co.won.service;

import kr.co.won.domain.type.SearchType;
import kr.co.won.dto.ArticleDomainDto;
import kr.co.won.dto.ArticleUpdateDto;
import kr.co.won.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDomainDto> searchArticles(SearchType title, String search_title) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleDomainDto searchArticle(long l) {
        return null;
    }

    public void saveArticle(ArticleDomainDto newArticle) {

    }

    public void updateArticle(Long articleId, ArticleUpdateDto updateArticle) {

    }

    public void deleteArticle(Long articleId) {

    }
}
