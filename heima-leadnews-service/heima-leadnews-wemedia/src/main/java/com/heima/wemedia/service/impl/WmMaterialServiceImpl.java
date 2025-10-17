package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.utils.thread.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Slf4j
@Service
@Transactional
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {
    @Resource
    private WmMaterialMapper wmMaterialMapper;
    @Resource
    private FileStorageService fileStorageService;

/**
 * Uploads a picture file to the storage service
 *
 * @param multipartFile The file to be uploaded
 * @return ResponseResult containing the upload result (currently returns null)
 */
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        //检验参数
        if(multipartFile == null || multipartFile.getSize() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //上传文件到minio文件系统中
        String fileURL = "";
        String filePost = multipartFile.getOriginalFilename().split("\\.")[1];
        try {
            String filename = UUID.randomUUID().toString().substring(0, 8)+'.'+filePost;
            fileURL = fileStorageService.uploadImgFile("WmMaterial", "material" + filename, multipartFile.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传图片失败");
        }

        //插入数据到数据库：wm_material
        WmMaterial wmMaterial = WmMaterial.builder()
                .url(fileURL)
                .createdTime(new Date())
                .isCollection(false)
                .type((byte)0)
                .userId(WmThreadLocalUtil.getUser().getApUserId())
                .build();

        wmMaterialMapper.insertAndReturnKey(wmMaterial);

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",wmMaterial.getId());
        WmMaterial wmMaterialResult = wmMaterialMapper.selectOne(wrapper);
        return ResponseResult.okResult(wmMaterialResult);
    }

    /**
     * 实现逻辑：
     * 1. 开启对应的分页查询
     * 2. 根据条件进行查询
     * 3. 构建返回结果
     * @param wmMaterialDto
     * @return
     */
    @Override
    public ResponseResult customList(WmMaterialDto wmMaterialDto) {
        //检查并修复分页参数
        wmMaterialDto.checkParam();
        //开启对应的分页查询
        IPage iPage = new Page(wmMaterialDto.getPage(),wmMaterialDto.getSize());
        //根据条件进行查询
        LambdaQueryWrapper<WmMaterial> lq = new LambdaQueryWrapper<>();
        lq.eq(WmMaterial::getUserId, WmThreadLocalUtil.getUser().getApUserId());
        lq.eq(WmMaterial::getIsCollection, wmMaterialDto.getIsCollection());
        lq.orderByDesc(WmMaterial::getCreatedTime);

        iPage = page(iPage, lq);

        ResponseResult responseResult = new PageResponseResult(wmMaterialDto.getPage(),wmMaterialDto.getSize(),(int)iPage.getTotal());
        responseResult.setData(iPage.getRecords());
        return responseResult;
    }
}
