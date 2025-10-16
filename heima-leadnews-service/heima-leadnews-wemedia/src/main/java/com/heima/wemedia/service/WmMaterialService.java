package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {


    /**
     * 用于文件上传功能：
     * 实现逻辑：
     * 1. 通过minio进行本地存储
     * 2. 将上传的文件信息保存到数据库中
     * @param multipartFile
     * @return
     */
    ResponseResult uploadPicture(MultipartFile multipartFile);
}