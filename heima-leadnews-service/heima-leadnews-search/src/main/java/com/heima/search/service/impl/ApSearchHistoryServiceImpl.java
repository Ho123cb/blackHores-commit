package com.heima.search.service.impl;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.search.dtos.HistorySearchDto;
import com.heima.model.search.pojos.ApUserSearch;
import com.heima.model.user.pojos.ApUser;
import com.heima.search.service.ApSearchHistoryService;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ApSearchHistoryServiceImpl implements ApSearchHistoryService {
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * @return
     */
    @Override
    public ResponseResult load() {
        ApUser user = AppThreadLocalUtil.getUser();
        if(user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        Long id = user.getId();
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(id));
        query.with(Sort.by(Sort.Direction.DESC,"createdTime"));

        List<ApUserSearch> apUsers = mongoTemplate.find(query, ApUserSearch.class);
        return ResponseResult.okResult(apUsers);
    }

    @Override
    public ResponseResult del(HistorySearchDto dto) {
        if(dto.getId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = AppThreadLocalUtil.getUser();
        if(user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("UserId").is(user.getId()).and("id").is(dto.getId()));

        mongoTemplate.remove(query, ApUserSearch.class);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
