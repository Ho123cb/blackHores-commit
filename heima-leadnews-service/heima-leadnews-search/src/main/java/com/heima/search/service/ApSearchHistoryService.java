package com.heima.search.service;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.HistorySearchDto;

public interface ApSearchHistoryService {
    /**
     * 用于历史搜索记录加载
     * @return
     */
    ResponseResult load();

    /**
     * 用于历史搜索记录删除
     * @param dto
     * @return
     */
    ResponseResult del(HistorySearchDto dto);
}
