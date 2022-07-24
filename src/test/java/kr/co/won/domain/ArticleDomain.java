package kr.co.won.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_article")
public class ArticleDomain implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 제목

    private String content; // 내용

    private String hashTag; // 해시태그

    private LocalDateTime createdAt; // 생성 일시

    private String createdBy; // 생성자

    private LocalDateTime updatedAt; // 수정 일시

    private String updatedBy; // 수정자
}
