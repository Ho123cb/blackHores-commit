package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

import java.lang.reflect.InvocationTargetException;

public interface WmNewsService extends IService<WmNews> {


    /**
     * 查询自媒体文章列表： 分页+条件
     * @param wmNewsPageReqDto
     * @return
     */
    ResponseResult customList(WmNewsPageReqDto wmNewsPageReqDto);

    /**
     * 提交文章
     * @param dto
     * @return
     */
    ResponseResult submit(WmNewsDto dto) ;
}
