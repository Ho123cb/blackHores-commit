package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;

public interface ArticleSearchAssociateService {
    /**
     * 联想搜索
     * @param  userSearchDto
     * @return
     */
    ResponseResult search(UserSearchDto userSearchDto);
}
