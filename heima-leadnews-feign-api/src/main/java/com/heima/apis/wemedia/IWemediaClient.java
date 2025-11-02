package com.heima.apis.wemedia;

import com.heima.model.admin.dtos.ChannelDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("leadnews-wemedia")
public interface IWemediaClient {
    @PostMapping("/api/v1/channel/list")
    public ResponseResult list(@RequestBody ChannelDto dto);

    @GetMapping("/api/v1/channel/channels")
    public ResponseResult channels();



    }
