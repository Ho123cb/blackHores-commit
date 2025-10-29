package com.heima.behavior.service.impl;

import com.heima.apis.article.ArticleOpenFeignClient;
import com.heima.behavior.service.LikeSerivce;
import com.heima.common.cache.CacheService;
import com.heima.common.constants.ActionConstants;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
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
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        //1.判断用户是否登录
        ApUser user = AppThreadLocalUtil.getUser();
        if( user == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        //2.判断文章是否存在
        ApArticle article =  articleOpenFeignClient.findOne(dto.getArticleId());
        if(article == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        //3.判断用户的行为,点赞或取消点赞
        String token = cacheService.tryLock("Like_TASK", 1000 * 30);
        if(dto.getOperation() == ActionConstants.Like_OPERATINO_CLICK) {
            cacheService.set(getCacheKey(user.getId() + "", dto.getType() + "", ActionConstants.Like_OPERATINO_CLICK + "", dto.getArticleId() + ""), ActionConstants.Like_OPERATINO_CLICK + "");
        } else {
            cacheService.set(getCacheKey(user.getId() + "", dto.getType() + "", ActionConstants.Like_OPERATINO_CLICK + "", dto.getArticleId() + ""), ActionConstants.Like_OPERATINO_UN_CLICK + "");
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    public String getCacheKey(String userId, String type, String action,String articleId) {
        return String.join(":",userId, type, action, articleId);
    }
}
