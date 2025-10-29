package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.pojos.ApAuthor;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * APP文章作者信息表 Mapper 接口
 * </p>
 *
 * @author finnhu
 * @since 2025-10-29
 */
@Mapper
public interface ApAuthorMapper extends BaseMapper<ApAuthor> {

}
