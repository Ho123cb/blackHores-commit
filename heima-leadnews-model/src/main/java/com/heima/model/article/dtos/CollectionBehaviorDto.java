package com.heima.model.article.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CollectionBehaviorDto {
    private Long entryId;
    private Long operation;
    private Date publishedTime;
    private Short type;
}
