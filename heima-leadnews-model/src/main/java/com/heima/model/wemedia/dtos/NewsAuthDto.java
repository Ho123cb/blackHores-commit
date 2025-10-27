package com.heima.model.wemedia.dtos;

import lombok.Data;

@Data
public class NewsAuthDto {
    private Integer id;
    private String msg;
    private  Integer page;
    private  Integer size;
    private  Integer status;
    private  String title;

    public void checkPage() {
        page = page == null || page <= 0 ? 1 : page;
        size = size == null || size <= 0 ? 10 : size;
    }
}
