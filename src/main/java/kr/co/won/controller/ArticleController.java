package kr.co.won.controller;

import kr.co.won.domain.constant.FormStatus;
import kr.co.won.domain.type.SearchType;
import kr.co.won.dto.ArticleDomainDto;
import kr.co.won.dto.ArticleWithCommentsDto;
import kr.co.won.dto.request.ArticleRequest;
import kr.co.won.dto.response.ArticleResponse;
import kr.co.won.dto.response.ArticleWithCommentsResponse;
import kr.co.won.dto.security.BoardPrincipal;
import kr.co.won.service.ArticleService;
import kr.co.won.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    private final PaginationService paginationService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, direction = Sort.Direction.DESC, sort = "createdAt") Pageable pageable,
            ModelMap modelMap
    ) {
        Page<ArticleResponse> articleResponses = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articleResponses.getTotalPages());

        modelMap.addAttribute("paginationBarNumbers", barNumbers);
        modelMap.addAttribute("articles", articleResponses);
        modelMap.addAttribute("searchTypes", SearchType.values());
        return "articles/index";
    }

    @GetMapping(path = "/{articleId}")
    public String article(@PathVariable(name = "articleId") Long id, ModelMap modelMap) {
        ArticleWithCommentsDto articleWithComments = articleService.getArticleWithComments(id);
        ArticleWithCommentsResponse findArticle = ArticleWithCommentsResponse.from(articleWithComments);
        modelMap.addAttribute("article", findArticle);
        modelMap.addAttribute("articleComments", findArticle.articleCommentsResponse());
        modelMap.addAttribute("totalCount", articleService.getArticleCount());
        return "articles/detail";
    }

    @GetMapping(path = "/search-hashtag")
    public String searchHashTag(
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap modelMap
    ) {
        Page<ArticleResponse> articleResponses = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articleResponses.getTotalPages());
        List<String> hashTags = articleService.getHashTags();

        modelMap.addAttribute("articles", articleResponses);
        modelMap.addAttribute("hashTags", hashTags);
        modelMap.addAttribute("paginationBarNumbers", barNumbers);
        modelMap.addAttribute("searchType", SearchType.HASHTAG);
        return "articles/search-hashtag";
    }

    @GetMapping(path = "/form")
    public String articleForm(ModelMap modelMap) {
        modelMap.addAttribute("formStatus", FormStatus.CREATE);
        return "articles/form";
    }

    @PostMapping(path = "/form")
    public String postNewArticle(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleRequest articleRequest
    ) {
        articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));
        return "redirect:/articles";
    }


    @GetMapping("/{articleId}/form")
    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

        map.addAttribute("article", article);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "articles/form";
    }

    @PostMapping("/{articleId}/form")
    public String updateArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleRequest articleRequest
    ) {
        articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/{articleId}/delete")
    public String deleteArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal
    ) {
        articleService.deleteArticle(articleId, boardPrincipal.getUsername());

        return "redirect:/articles";
    }

}
