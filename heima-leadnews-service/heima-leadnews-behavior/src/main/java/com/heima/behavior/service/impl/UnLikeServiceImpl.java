package com.heima.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.apis.article.ArticleOpenFeignClient;
import com.heima.behavior.service.UnLikeService;
import com.heima.common.cache.CacheService;
import com.heima.common.constants.ActionConstants;
import com.heima.common.constants.ArticleConstants;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.behavior.dtos.UnLikeBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.behavior.CacheUtils;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UnLikeServiceImpl implements UnLikeService {

    @Resource
    private CacheService cacheService;
    @Resource
    private ArticleOpenFeignClient articleOpenFeignClient;
    @Override
    public ResponseResult unLikeBehavior(UnLikeBehaviorDto dto) {
        if (dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        if (dto.getType() == 0) {
            log.info("保存当前key:{} ,{}, {}", dto.getArticleId(), user.getId(), dto);
            cacheService.hPut(ActionConstants.ACTION_TYPE_UNLIKE + dto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(dto));
        } else {
            log.info("删除当前key:{} ,{}, {}", dto.getArticleId(), user.getId(), dto);
            cacheService.hDelete(ActionConstants.ACTION_TYPE_UNLIKE + dto.getArticleId().toString(), user.getId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
