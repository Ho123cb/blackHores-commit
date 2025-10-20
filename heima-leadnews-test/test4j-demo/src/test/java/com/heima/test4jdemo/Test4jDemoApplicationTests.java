package com.heima.test4jdemo;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;
import java.io.File;

@SpringBootTest
class Test4jDemoApplicationTests {

    @Test
    void contextLoads() {
    }





    @Test
    public void test(){
        try {
            //获取本地图片
            File file = new File("C:\\Users\\胡明飞\\Documents\\Pictures\\Screenshots\\屏幕截图 2024-05-25 233050.png");
            //创建Tesseract对象
            ITesseract tesseract = new Tesseract();
            //设置字体库路径
            tesseract.setDatapath("E:\\FinnHu\\Code\\java\\javaClassStudy\\project\\blackHorseCommit\\resource");
            //中文识别
            tesseract.setLanguage("chi_sim");
            //执行ocr识别
            String result = tesseract.doOCR(file);
            //替换回车和tal键  使结果为一行
            result = result.replaceAll("\\r|\\n","-").replaceAll(" ","");
            System.out.println("识别的结果为："+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
