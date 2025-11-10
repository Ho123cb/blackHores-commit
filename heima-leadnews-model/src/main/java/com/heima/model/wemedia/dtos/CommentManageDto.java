package com.heima.model.wemedia.dtos;

import lombok.Data;

@Data
public class CommentManageDto {
    private String beginDate;
    private String endDate;
    private Integer size;
    private Integer page;

    public void checkParams() {
        size = (size == null || size <= 0) ? 10 : size;
        page = (page == null || page <= 0) ? 1 : page;
    }
}
