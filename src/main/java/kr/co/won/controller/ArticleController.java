package kr.co.won.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * /articles
 * /articles/{article-id}
 * /articles/search
 * /articles/search-hashtag
 */

@Controller
@RequestMapping(path = "/articles")
public class ArticleController {

    @GetMapping
    public String articles(ModelMap modelMap) {
        modelMap.addAttribute("articles", List.of());
        return "articles/index";
    }

    @GetMapping(path = "/{articleId}")
    public String article(@PathVariable(name = "articleId") Long id, ModelMap modelMap) {
        modelMap.addAttribute("article", null);
        modelMap.addAttribute("articleComments", List.of());
        return "articles/detail";
    }

}
