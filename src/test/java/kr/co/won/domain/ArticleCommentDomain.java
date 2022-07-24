package kr.co.won.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ArticleCommentDomain implements Serializable {

    private Long id;

    private Long articleId;

    private String content;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

}
