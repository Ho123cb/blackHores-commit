package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ArticleManageService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.vos.ArticleManageVo;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.CommentManageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.List;

@Service
@Slf4j
public class ArticleManageServiceImpl implements ArticleManageService {
    @Resource
    private ApArticleMapper apArticleMapper;

    @Override
    public ResponseResult list(CommentManageDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        dto.checkParams();

        IPage<ArticleManageVo> page = new Page<>(dto.getPage(), dto.getSize());

        LambdaQueryWrapper<ApArticle> lq = new LambdaQueryWrapper<>();
        lq.gt(ApArticle::getPublishTime, dto.getBeginDate())
            .lt(ApArticle::getPublishTime, dto.getEndDate())
            .orderByDesc(ApArticle::getPublishTime);

        IPage<ArticleManageVo> apArticleIPage = apArticleMapper.customSelectPage(page, lq);

        ResponseResult<List<ArticleManageVo>> rs = new PageResponseResult(dto.getPage(),dto.getSize(),(int)apArticleIPage.getTotal());
        return rs.ok(apArticleIPage.getRecords());
    }
}
