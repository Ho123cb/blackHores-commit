package com.heima.comment.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.apis.user.IUserClient;
import com.heima.common.aliyun.CustomGreenTextScan;
import com.heima.common.constants.ApCommentConstants;
import com.heima.common.exception.CustomException;
import com.heima.model.comment.dtos.CommentDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import com.heima.model.comment.pojos.ApComment;
import com.heima.model.comment.pojos.ApCommentLike;
import com.heima.model.comment.vos.ApCommentOperationVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.vos.UserAuthorVo;
import com.heima.comment.service.CommentService;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private CustomGreenTextScan customGreenTextScan;
    @Resource
    private IUserClient userClient;


    @Override
    public ResponseResult save(CommentSaveDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        //判断当前用户是否登录
        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        
        if(dto.getContent().length() > ApCommentConstants.COMMENT_CONTENT_LENGTH)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"评论内容过长");

        ApComment apComment = setApComment(dto);
        //内容垃圾检测
        apComment.setEntryId(dto.getArticleId().toString());
        apComment.setContent(dto.getContent());
        handleTextScan(dto.getContent(), apComment);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult like(CommentLikeDto dto) {
        ApUser user = AppThreadLocalUtil.getUser();
        if(user == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);

        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        //修改ap_comment
        ApComment apComment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);
        int dif = dto.getOperation() == 0?1:-1;
        apComment.setLikes(apComment.getLikes() - dif);
        mongoTemplate.save(apComment);
        //插入ap_comment_like
        if(dto.getOperation() == 0) {
            if (mongoTemplate.exists(Query.query(Criteria.where("commentId").is(dto.getCommentId()).and("authorId").is(user.getId())), ApCommentLike.class))
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "已经点赞过了");
            ApCommentLike apCommentLike = new ApCommentLike();
            apCommentLike.setCommentId(dto.getCommentId());
            apCommentLike.setAuthorId(user.getId());
            mongoTemplate.save(apCommentLike);
        }else if( dto.getOperation() == 1) {
            mongoTemplate.remove(Query.query(Criteria.where("commentId").is(dto.getCommentId()).and("authorId").is(user.getId())), ApCommentLike.class);
        }


        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult list(CommentDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        //注意：这里不登录也能获取到
        //获取当前用户
        ApUser user = AppThreadLocalUtil.getUser();
        Boolean flag = user!=null?true:false;
        //查询ap_comment表
        Query query = new Query();
        query.addCriteria(Criteria.where("entryId").is(dto.getArticleId().toString()))
            .addCriteria(Criteria.where("type").is(ApCommentConstants.COMMENT_STATUS_OPEN))
            .addCriteria(Criteria.where("createdTime").gt(dto.getMinDate()))
            .with(Sort.by(Sort.Direction.DESC,"createdTime"))
        //分页查询实现：通过skip+limit
            .skip(0)
            .limit(10);
        List<ApComment> apCommentList = mongoTemplate.find(query, ApComment.class);
        //准备扩充operation字段
        List<ApCommentOperationVo> collect = apCommentList.stream().map(item -> {
            ApCommentOperationVo vo = new ApCommentOperationVo();
            try {
                BeanUtils.copyProperties(vo, item);
                query.addCriteria(Criteria.where("commentId").is(item.getId().toString()).and("authorId").is(user.getId().toString()));
                Integer operation = mongoTemplate.exists(query, ApCommentLike.class) == true ? 0 : 1;
                vo.setOperation(operation.shortValue());
                return vo;
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
            }
        }).collect(Collectors.toList());

        return ResponseResult.okResult(collect);
    }

    private ApComment setApComment(CommentSaveDto dto) {
        //插入到MongoDB表ap_comment中
        ApComment apComment = new ApComment();
        apComment.setType(ApCommentConstants.COMMENT_STATUS_WAIT);
        apComment.setContent(dto.getContent());
        apComment.setEntryId(dto.getArticleId().toString());
        //获取作者信息填入
        Object data = userClient.queryName(AppThreadLocalUtil.getUser().getId()).getData();
        String jsonString = JSON.toJSONString(data);
        UserAuthorVo articleAuthorVos = JSON.parseObject(jsonString, UserAuthorVo.class);
        apComment.setAuthorId(articleAuthorVos.getAuthorId().toString());
        apComment.setAuthorName(articleAuthorVos.getAuthorName());
        mongoTemplate.save(apComment);
        return apComment;
    }


    /**
     * 审核纯文本内容
     * @param content
     * @param apComment
     * @return
     */
    private boolean handleTextScan(String content, ApComment apComment) {

        boolean flag = true;


        try {
            Map map = customGreenTextScan.greeTextScan((content));
            if(map != null){
                //审核失败
                if(map.get("suggestion").equals("block")){
                    flag = false;
                    updateApComment(apComment, ApCommentConstants.COMMENT_STATUS_DELETE);
                }

                //不确定信息  需要人工审核
                if(map.get("suggestion").equals("review")){
                    flag = false;
                    updateApComment(apComment, ApCommentConstants.COMMENT_STATUS_REJECT);
                }

                //审核通过
                if(map.get("suggestion").equals("pass")){
                    updateApComment(apComment, ApCommentConstants.COMMENT_STATUS_OPEN);
                }
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        return flag;

    }

    /**
     * 修改apComment的数据
     * @param apComment
     * @param commentStatusOpen
     */
    private void updateApComment(ApComment apComment, Short commentStatusOpen) {
        apComment.setType(commentStatusOpen);
        mongoTemplate.save(apComment);
    }
}
