package com.heima.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.article.service.ArticleCollectionSerivce;
import com.heima.common.cache.CacheService;
import com.heima.common.constants.ActionConstants;
import com.heima.model.article.dtos.CollectionBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ArticleCollectionSerivceImpl implements ArticleCollectionSerivce {
    @Resource
    private CacheService cacheService;

    /**
     * 1. 判断参数是否不为空
     * 2. 判断当前用户是否登录
     * 3. 判断当前用户是否已经收藏过该文章
     * 4. 如果已经收藏过，则取消收藏，否则收藏
     * 5. 返回结果
     * 存储到redis的逻辑：Hset
     * @param dto
     * @return
     */
    @Override
    public ResponseResult collectionBehavior(CollectionBehaviorDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        ApUser user = AppThreadLocalUtil.getUser();
        if(user == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);

        boolean flag = cacheService.hExists(ActionConstants.ACTION_TYPE_COLLECTION + dto.getEntryId().toString(), user.getId().toString());
        if(flag)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);

        if(dto.getOperation() == 0) {
            //执行收藏逻辑
            cacheService.hPut(ActionConstants.ACTION_TYPE_COLLECTION + dto.getEntryId().toString(), user.getId().toString(), JSON.toJSONString(dto));
        } else if(dto.getOperation() == 1) {
            //执行取消收藏逻辑
            cacheService.hDelete(ActionConstants.ACTION_TYPE_COLLECTION + dto.getEntryId().toString(), user.getId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
