package com.heima.article.service.impl;

import com.heima.article.mapper.ApAuthorMapper;
import com.heima.article.service.IApAuthorService;
import com.heima.model.article.pojos.ApAuthor;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP文章作者信息表 服务实现类
 * </p>
 *
 * @author finnhu
 * @since 2025-10-29
 */
@Service
public class ApAuthorServiceImpl extends ServiceImpl<ApAuthorMapper, ApAuthor> implements IApAuthorService {

}
