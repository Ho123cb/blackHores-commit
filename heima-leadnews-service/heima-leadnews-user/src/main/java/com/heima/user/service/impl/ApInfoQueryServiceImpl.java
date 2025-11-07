package com.heima.user.service.impl;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.vos.UserAuthorVo;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApInfoQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ApInfoQueryServiceImpl implements ApInfoQueryService {
    @Resource
    private ApUserMapper apUserMapper;

    @Override
    public ResponseResult queryName(Long id) {
        ApUser apUser = apUserMapper.selectById(id);
        UserAuthorVo vo = new UserAuthorVo();
        vo.setAuthorName(apUser.getName());
        vo.setAuthorId(id);
        return ResponseResult.okResult(vo);
    }
}
