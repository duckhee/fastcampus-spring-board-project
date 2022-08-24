package kr.co.won.service;

import kr.co.won.dto.ArticleCommentDomainDto;
import kr.co.won.repository.ArticleCommentRepository;
import kr.co.won.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleCommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public List<ArticleCommentDomainDto> searchArticleComments(Long articleId) {
        return null;
    }
}
