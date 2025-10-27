package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.dtos.WmNewsUpOrDownDto;
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

    /**
     * 上架或下架
     * @param dto
     * @return
     */
    ResponseResult downOrUp(WmNewsUpOrDownDto dto);

    /**
     * 用于文章的分页+模糊查询，多表查询，多加入返回字段content
     * @param dto
     * @return
     */
    ResponseResult listVO(NewsAuthDto dto);

    /**
     * 查询单个文章详情
     * @param id
     * @return
     */
    ResponseResult oneVO(Integer id);

    /**
     * 审核状态修改
     * @param dto
     * @param wmNewsReviewFail
     * @return
     */
    ResponseResult updateStatus(NewsAuthDto dto, Short wmNewsReviewFail);
}
