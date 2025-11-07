package com.heima.comment.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.heima.apis.user.IUserClient;
import com.heima.comment.service.CommentReplyService;
import com.heima.comment.service.CommentService;
import com.heima.common.aliyun.CustomGreenTextScan;
import com.heima.common.constants.ApCommentConstants;
import com.heima.common.exception.CustomException;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentRepayDto;
import com.heima.model.comment.dtos.CommentRepayLikeDto;
import com.heima.model.comment.dtos.CommentRepaySaveDto;
import com.heima.model.comment.pojos.ApComment;
import com.heima.model.comment.pojos.ApCommentRepay;
import com.heima.model.comment.pojos.ApCommentRepayLike;
import com.heima.model.comment.vos.ApCommentRepayOperationVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.vos.UserAuthorVo;
import com.heima.utils.thread.AppThreadLocalUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.util.BeanUtil;
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
public class CommentReplyServiceImpl implements CommentReplyService {
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private CustomGreenTextScan customGreenTextScan;
    @Resource
    private IUserClient userClient;
    @Override
    public ResponseResult save(CommentRepaySaveDto dto) {
        ApUser user = AppThreadLocalUtil.getUser();
        if(user == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);

        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        //判断是否已经回复过该评论了
        Query query = new Query();
        query.addCriteria(Criteria.where("commentId").is(dto.getCommentId()))
                .addCriteria(Criteria.where("authorId").is(user.getId()));
        ApCommentRepay apCommentRepay = mongoTemplate.findOne(query, ApCommentRepay.class);
        if(apCommentRepay != null)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_STATUS_NOT_ALLOW,"您已经评论过！");

        //审核回复的内容
        try {
            Map map = customGreenTextScan.greeTextScan(dto.getContent());
            if(map == null || map.get("suggestion").equals("block"))
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_STATUS_NOT_ALLOW,"数据审核失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(AppHttpCodeEnum.SERVER_ERROR);
        }
        apCommentRepay = new ApCommentRepay();
        apCommentRepay.setCommentId(dto.getCommentId());
        apCommentRepay.setAuthorId(user.getId());
        apCommentRepay.setContent(dto.getContent());

        //填充authorName
        Object data = userClient.queryName(user.getId()).getData();
        String jsonString = JSON.toJSONString(data);
        UserAuthorVo userAuthorVo = JSON.parseObject(jsonString, UserAuthorVo.class);
        apCommentRepay.setAuthorName(userAuthorVo.getAuthorName());

        mongoTemplate.save(apCommentRepay);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult like(CommentRepayLikeDto dto) {
        ApUser user = AppThreadLocalUtil.getUser();
        if(user == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);

        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        // 插入ap_comment_repay_like
        //1.先判断是否已经点赞了
        Query query = new Query();
        query.addCriteria(Criteria.where("commentRepayId").is(dto.getCommentRepayId().toString())
                .and("authorId").is(user.getId()));
        ApCommentRepayLike apCommentRepayLikes = mongoTemplate.findOne(query, ApCommentRepayLike.class);
        if(apCommentRepayLikes != null)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_STATUS_NOT_ALLOW,"您已经点赞过！");

        //2.插入点赞记录
        apCommentRepayLikes = new ApCommentRepayLike();
        apCommentRepayLikes.setCommentRepayId(dto.getCommentRepayId());
        apCommentRepayLikes.setAuthorId(user.getId());
        mongoTemplate.save(apCommentRepayLikes);
        // 修改ap_comment_repay表中的likes字段
        query = new Query();
        query.addCriteria(Criteria.where("id").is(dto.getCommentRepayId()));
        ApCommentRepay apCommentRepay = mongoTemplate.findOne(query, ApCommentRepay.class);
        apCommentRepay.setLikes(apCommentRepay.getLikes() + 1);
        mongoTemplate.save(apCommentRepay);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult list(CommentRepayDto dto) {
        if(dto == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        Query query = new Query();
        query.addCriteria(Criteria.where("commentId").is(dto.getCommentId()))
                .addCriteria(Criteria.where("createdTime").gt(dto.getMinDate()))
                .skip(0)
                .limit(dto.getSize())
                .with(Sort.by(Sort.Direction.DESC,"createdTime"));
        List<ApCommentRepay> apCommentRepays = mongoTemplate.find(query, ApCommentRepay.class);

        List<ApCommentRepayOperationVo> collect = apCommentRepays.stream().map(item -> {
            ApCommentRepayOperationVo vo = null;
            try {
                vo = new ApCommentRepayOperationVo();
                BeanUtils.copyProperties(vo, item);
                Query query1 = new Query();
                query1.addCriteria(Criteria.where("commentRepayId").is(item.getId()))
                        .addCriteria(Criteria.where("authorId").is(AppThreadLocalUtil.getUser().getId().toString()));
                ApCommentRepayLike apCommentRepayLike = mongoTemplate.findOne(query1, ApCommentRepayLike.class);
                short operation = (short) (apCommentRepayLike == null ? 1 : 0);
                vo.setOperation(operation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return vo;
        }).collect(Collectors.toList());
        return ResponseResult.okResult(collect);
    }


}
