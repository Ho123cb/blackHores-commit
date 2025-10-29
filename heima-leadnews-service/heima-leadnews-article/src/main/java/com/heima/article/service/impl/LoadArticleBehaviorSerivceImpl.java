package com.heima.article.service.impl;

import com.heima.article.service.LoadArticleBehaviorSerivce;
import com.heima.common.cache.CacheService;
import com.heima.common.constants.ActionConstants;
import com.heima.model.article.dtos.ArticleInfoDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoadArticleBehaviorSerivceImpl implements LoadArticleBehaviorSerivce {
    @Resource
    private CacheService cacheService;


    @Override
    public ResponseResult loadArticleBehavior(ArticleInfoDto dto) {
        //0.检查参数
        if (dto == null || dto.getArticleId() == null || dto.getAuthorId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //{ "isfollow": true, "islike": true,"isunlike": false,"iscollection": true }
        boolean isfollow = false, islike = false, isunlike = false, iscollection = false;

        ApUser user = AppThreadLocalUtil.getUser();
        if(user != null){
            //喜欢行为
            String likeBehaviorJson = (String) cacheService.hGet(ActionConstants.ACTION_TYPE_LIKE + dto.getArticleId().toString(), user.getId().toString());
            if(StringUtils.isNotBlank(likeBehaviorJson)){
                islike = true;
            }
            //不喜欢的行为
            String unLikeBehaviorJson = (String) cacheService.hGet(ActionConstants.ACTION_TYPE_UNLIKE + dto.getArticleId().toString(), user.getId().toString());
            if(StringUtils.isNotBlank(unLikeBehaviorJson)){
                isunlike = true;
            }
            //是否收藏
            String collctionJson = (String) cacheService.hGet(ActionConstants.ACTION_TYPE_COLLECTION +user.getId(),dto.getArticleId().toString());
            if(StringUtils.isNotBlank(collctionJson)){
                iscollection = true;
            }

            //是否关注
            Double score = cacheService.zScore(ActionConstants.APUSER_FOLLOW_RELATION + user.getId(), dto.getAuthorId().toString());
            System.out.println(score);
            if(score != null){
                isfollow = true;
            }

        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isfollow", isfollow);
        resultMap.put("islike", islike);
        resultMap.put("isunlike", isunlike);
        resultMap.put("iscollection", iscollection);

        return ResponseResult.okResult(resultMap);
    }
}
