package kr.co.won.controller;

import kr.co.won.domain.type.SearchType;
import kr.co.won.dto.ArticleWithCommentsDto;
import kr.co.won.dto.response.ArticleCommentResponse;
import kr.co.won.dto.response.ArticleResponse;
import kr.co.won.dto.response.ArticleWithCommentsResponse;
import kr.co.won.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * /articles
 * /articles/{article-id}
 * /articles/search
 * /articles/search-hashtag
 */

@Controller
@RequestMapping(path = "/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, direction = Sort.Direction.DESC, sort = "createdAt") Pageable pageable,
            ModelMap modelMap
    ) {

        modelMap.addAttribute("articles", articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from));
        return "articles/index";
    }

    @GetMapping(path = "/{articleId}")
    public String article(@PathVariable(name = "articleId") Long id, ModelMap modelMap) {
        ArticleWithCommentsDto articleWithComments = articleService.getArticleWithComments(id);
        ArticleWithCommentsResponse findArticle = ArticleWithCommentsResponse.from(articleWithComments);
        modelMap.addAttribute("article", findArticle);
        modelMap.addAttribute("articleComments", findArticle.articleCommentsResponse());
        return "articles/detail";
    }

}
