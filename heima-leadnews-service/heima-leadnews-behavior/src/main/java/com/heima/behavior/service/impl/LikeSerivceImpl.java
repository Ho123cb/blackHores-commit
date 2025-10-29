package com.heima.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.apis.article.ArticleOpenFeignClient;
import com.heima.behavior.service.LikeSerivce;
import com.heima.common.cache.CacheService;
import com.heima.common.constants.ActionConstants;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.behavior.CacheUtils;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class LikeSerivceImpl implements LikeSerivce {
    @Resource
    private CacheService cacheService;
    @Resource
    private ArticleOpenFeignClient articleOpenFeignClient;
    @Override
    public ResponseResult like(LikesBehaviorDto dto) {
        //1.检查参数
        if (dto == null || dto.getArticleId() == null || checkParam(dto)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.是否登录
        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //3.点赞  保存数据
        if (dto.getOperation() == 0) {
            Object obj = cacheService.hGet(ActionConstants.ACTION_TYPE_LIKE + dto.getArticleId().toString(), user.getId().toString());
            if (obj != null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "已点赞");
            }
            // 保存当前key
            log.info("保存当前key:{} ,{}, {}", dto.getArticleId(), user.getId(), dto);
            cacheService.hPut(ActionConstants.ACTION_TYPE_LIKE + dto.getArticleId().toString(), user.getId().toString(), JSON.toJSONString(dto));
        } else {
            // 删除当前key
            log.info("删除当前key:{}, {}", dto.getArticleId(), user.getId());
            cacheService.hDelete(ActionConstants.ACTION_TYPE_LIKE + dto.getArticleId().toString(), user.getId().toString());
        }


        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
    /**
     * 检查参数
     *
     * @return
     */
    private boolean checkParam(LikesBehaviorDto dto) {
        if (dto.getType() > 2 || dto.getType() < 0 || dto.getOperation() > 1 || dto.getOperation() < 0) {
            return true;
        }
        return false;
    }
}
