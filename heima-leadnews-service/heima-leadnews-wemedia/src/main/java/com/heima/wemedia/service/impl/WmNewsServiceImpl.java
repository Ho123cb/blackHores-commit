package com.heima.wemedia.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
@Transactional
public class WmNewsServiceImpl  extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {
    @Resource
    private WmNewsMapper wmNewsMapper;


    @Override
    public ResponseResult customList(WmNewsPageReqDto dto) {
        //1.分页功能：
        dto.checkParam();
        IPage page = new Page<>(dto.getPage(), dto.getSize());

        //2.构建查询参数:
        LambdaQueryWrapper<WmNews> lq = new LambdaQueryWrapper();
        lq.eq( dto.getStatus() != null, WmNews::getStatus, dto.getStatus());
        lq.gt( dto.getBeginPubDate() != null, WmNews::getSubmitedTime, dto.getBeginPubDate());
        lq.lt( dto.getEndPubDate() != null, WmNews::getSubmitedTime, dto.getEndPubDate());
        lq.eq( dto.getChannelId() != null, WmNews::getChannelId, dto.getChannelId());
        lq.like( dto.getKeyword() != null, WmNews::getTitle, dto.getKeyword());
        lq.orderByDesc(WmNews::getCreatedTime);

        //3.执行查询：
        page = page( page, lq);
        ResponseResult result = new PageResponseResult((int) page.getCurrent(), (int) page.getSize(), (int)page.getTotal());
        result.setData(page.getRecords());
        return result;
    }
}
