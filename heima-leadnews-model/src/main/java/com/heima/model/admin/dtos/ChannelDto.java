package com.heima.model.admin.dtos;

import lombok.Data;

@Data
public class ChannelDto {
    private String name;
    private Integer size;
    private Integer page;

    public void checkPage() {
        if(size==null || size <= 0) {
            size = 10;
        }
        if(page == null || page <= 0) {
            page = 1;
        }
    }
}
