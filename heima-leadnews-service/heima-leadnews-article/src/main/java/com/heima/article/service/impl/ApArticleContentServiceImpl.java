package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.service.IApArticleContentService;
import com.heima.model.article.pojos.ApArticleContent;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP已发布文章内容表 服务实现类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-13
 */
@Service
public class ApArticleContentServiceImpl extends ServiceImpl<ApArticleContentMapper, ApArticleContent> implements IApArticleContentService {

}
