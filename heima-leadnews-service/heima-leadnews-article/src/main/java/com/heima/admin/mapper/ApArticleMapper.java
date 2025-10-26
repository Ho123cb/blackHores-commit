package com.heima.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章信息表，存储已发布的文章 Mapper 接口
 * </p>
 *
 * @author finnhu
 * @since 2025-10-13
 */
@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    /**
     *  type: 1 表示 下翻 , 2 表示 上翻
     * @param dto
     * @param type
     * @return
     */
    public List<ApArticle> loadArticleList(@Param("dto") ArticleHomeDto dto, @Param("type") Short type);

    void insertAndReturnId(ApArticle apArticle);
}
