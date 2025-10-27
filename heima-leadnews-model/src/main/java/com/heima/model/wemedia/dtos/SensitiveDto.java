package com.heima.model.wemedia.dtos;

import lombok.Data;

@Data
public class SensitiveDto {
    private String name;
    private Integer size;
    private Integer page;

    public void checkParam(){
        size =  size == null || size <= 0 ? 10 : size;
        page = page == null || page <= 0 ? 1 : page;
    }
}
