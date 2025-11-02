package com.heima.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.JsonObject;
import com.heima.apis.wemedia.IWemediaClient;
import com.heima.article.service.ArticleHotJobService;
import com.heima.article.service.IApArticleService;
import com.heima.common.cache.CacheService;
import com.heima.common.constants.ArticleConstants;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.vos.ApArticleVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmChannel;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.Local;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.net.nntp.Article;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleHotJobServiceImpl implements ArticleHotJobService {
    @Resource
    private CacheService cacheService;
    @Resource
    private IApArticleService apArticleService;
    @Resource
    private IWemediaClient iWemediaClient;



    /**
     * 1.查询出所有的频道集合
     * 2.依次遍历频道对应的文章，根据最热评判准则依次计算并加入一个集合，最后判断取出前三十个（五天之内）放入缓存
     * TODO 使用算法实现最热文章分数比较
     * 3.查询所有文章并对最热进行比较，取三十个放缓存
     * 存储规则：hotArticle+频道id ， 文章内容
     */
    @Override
    public void hotArticle() {
        log.info("开始计算文章热度");
        //1.查询出所有的频道集合
        // TODO 总结 object 转化为 希望值的方法
        Object data = iWemediaClient.channels().getData();
        String jsonString = JSON.toJSONString(data);
        List<WmChannel> wmChannels = JSON.parseArray(jsonString, WmChannel.class);
        //2.依次遍历频道对应的文章，根据最热评判准则依次计算并加入一个集合，最后判断取出前三十个（五天之内）放入缓存
        getHotArticleToCache(wmChannels);
        //存储规则：hotArticle+频道id ， 文章内容
    }

    public void getHotArticleToCache(List<WmChannel> wmChannels) {
        if(wmChannels != null &&  !wmChannels.isEmpty()) {
            //更新其他的频道（不包括首页推荐）
            for(WmChannel wmChannel : wmChannels) {
                List<ApArticle> apArticles = getApArticles(wmChannel);
                //为空则更新下一个频道
                if(apArticles == null || apArticles.isEmpty())
                    continue;
                calculateAndToCache(apArticles, wmChannel);
            }
        }
        //更新首页推荐
        List<ApArticle> apArticles = getApArticles(null);
        if(apArticles == null || apArticles.isEmpty())
            return;

        calculateAndToCache(apArticles, null);

    }

    private List<ApArticle> getApArticles(WmChannel wmChannel) {
        //1.查询五日所有的文章, 并计算和排序热度，取出其中前三十个即可
        LambdaQueryWrapper<ApArticle> lq = new LambdaQueryWrapper<>();
        Optional.ofNullable(wmChannel)
                .map(WmChannel::getId)
                .ifPresent(id -> lq.eq(ApArticle::getChannelId, id));
        lq.gt(ApArticle::getPublishTime, LocalDateTime.now().minusDays(5));
        List<ApArticle> apArticles = apArticleService.list(lq);
        return apArticles;
    }

    private void calculateAndToCache(List<ApArticle> apArticles, WmChannel wmChannel) {
        List<ApArticleVo> top30 = apArticles.stream()
                .map(src -> {
                    ApArticleVo vo = new ApArticleVo();
                    try {
                        // Apache 的顺序：target <- source
                        BeanUtils.copyProperties(vo, src);
                        vo.calculateScore();
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("copyProperties failed", e);
                    }
                    return vo;
                })
                .sorted(Comparator.comparing(
                        ApArticleVo::getScore,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .limit(30)
                .collect(Collectors.toList());
        String key = ArticleConstants.HOT_ARTICLE_FIRST_PAGE + (wmChannel != null ? wmChannel.getId() : ArticleConstants.DEFAULT_TAG);
        if(top30 != null && !top30.isEmpty())
            cacheService.set(key, JSON.toJSONString(top30));
    }


}
