package com.heima.wemedia;

import com.heima.common.aliyun.CustomGreenImageScan;
import com.heima.common.aliyun.CustomGreenTextScan;
import com.heima.common.aliyun.GreenImageScan;
import com.heima.common.aliyun.GreenTextScan;
import com.heima.file.service.FileStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Map;

@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class AliyunTest {

    @Autowired
    private CustomGreenTextScan customGreenTextScan;

    @Autowired
    private CustomGreenImageScan customGreenImageScan;

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void testScanText() throws Exception {
        Map map = customGreenTextScan.greeTextScan("我是一个好人,冰毒");
        System.out.println(map);
    }

    @Test
    public void testScanImage() throws Exception {
        byte[] bytes = fileStorageService.downLoadFile("http://192.168.200.130:9000/leadnews/WmMaterial/2025/10/16/material7fde7239.png");
        //通过bytes将文件存储到本地
        String filePath = "D://material7fde7239.png";
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(bytes);
        fileOutputStream.close();

        Map map = customGreenImageScan.greeImageScan(filePath);
        System.out.println(map);
    }
}