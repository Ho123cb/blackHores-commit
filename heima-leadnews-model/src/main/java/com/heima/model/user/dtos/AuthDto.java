package com.heima.model.user.dtos;

import lombok.Data;

@Data
public class AuthDto {
    private Integer id;
    private String msg;
    private Integer page;
    private Integer size;
    private Integer status;

    public void checkPage() {
        page = page == null  || page <= 0 ? 1 : page;
        size = size == null || size <= 0 ? 10 : size;
    }
}
