package com.heima.apis.user;

import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("leadnews-user")
public interface IUserClient {
    @ApiOperation("用户名字查询")
    @GetMapping("/api/v1/user/query/{id}")
    public ResponseResult queryName(@PathVariable("id") Long id);
}
