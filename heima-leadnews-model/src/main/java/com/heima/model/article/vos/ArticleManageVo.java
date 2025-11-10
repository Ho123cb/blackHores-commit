package com.heima.model.article.vos;

import lombok.Data;

@Data
public class ArticleManageVo {
    private String id;
    private String title;
    private Integer comments;
    private Boolean IsComment;
    private Integer createdTime;
}
