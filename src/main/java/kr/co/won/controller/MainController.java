package kr.co.won.controller;

import kr.co.won.dto.response.ArticleCommentResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * <p>
 * 메인 컨트롤러
 * </p>
 * 테스트 중입니다.
 */
@Controller
public class MainController {

    @GetMapping(path = "/")
    public String root() {
        return "redirect:/articles";
    }

    /**
     * 댓글 정보를 열람한다.
     *
     * @Param id 댓글 아이디
     * @return 댓글 알람
     */
    @ResponseBody
    @GetMapping(path = "/test-rest")
    public ArticleCommentResponse test(Long id) {
        return ArticleCommentResponse.of(id,
                "content",
                LocalDateTime.now(),
                "tester@co.kr",
                "terter",
                "tester");
    }
}
