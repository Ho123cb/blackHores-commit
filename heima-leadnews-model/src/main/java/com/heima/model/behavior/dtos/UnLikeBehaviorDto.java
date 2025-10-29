package com.heima.model.behavior.dtos;

import lombok.Data;

@Data
public class UnLikeBehaviorDto {
    private Long articleId;
    private Short type; //0不喜欢 1 取消不喜欢
}
