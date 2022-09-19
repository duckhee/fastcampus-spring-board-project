package kr.co.won.controller;

import kr.co.won.dto.UserAccountDto;
import kr.co.won.dto.request.ArticleCommentRequest;
import kr.co.won.dto.security.BoardPrincipal;
import kr.co.won.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;


    @PostMapping(path = "/new")
    public String postNewArticleComment(@AuthenticationPrincipal BoardPrincipal boardPrincipal, ArticleCommentRequest articleCommentRequest) {
        // TODO 인증 정보를 넣어줘야한다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));
        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping(path = "/{articleCommentId}/delete")
    public String deleteArticleComment(@PathVariable(name = "articleCommentId") Long commentId, @AuthenticationPrincipal BoardPrincipal boardPrincipal, Long articleId) {
        articleCommentService.deleteArticleComment(commentId, boardPrincipal.getUsername());
        return "redirect:/articles/" + articleId;
    }
}
