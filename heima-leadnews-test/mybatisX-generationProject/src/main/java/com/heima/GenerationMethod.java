package com.heima;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

import static com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder.packageConfig;

public class GenerationMethod {
    static String cpackage = "wemedia";
    static String  to = "E:\\FinnHu\\Code\\java\\javaClassStudy\\project\\blackHorseCommit\\code\\heima-leadnews\\heima-leadnews-test\\mybatisX-generationProject\\src\\main\\resources\\generation";
        public static void main(String[] args) {
                FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/leadnews-wemedia?characterEncoding=utf-8&userSSL=false", "root", "root")
                        .globalConfig(builder -> {
                            builder.author("finnhu") // 设置作者
                                    //.enableSwagger() // 开启 swagger 模式
                                    // 覆盖已生成文件
                                    .outputDir(to); // 指定输出目录
                        })
                        .packageConfig(builder -> {
                            builder.parent("com.heima") // 设置父包名
                                    .entity("model."+cpackage+".pojos")
                                    .service(cpackage+ "service")
                                    .serviceImpl(cpackage+ "service.impl")
                                    .pathInfo(Collections.singletonMap(OutputFile.xml, to)); // 设置mapperXml生成路径
                        })

                        .strategyConfig(builder -> {
                            builder.addInclude(
                                    "wm_sensitive");// 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                        })
                        .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                        .execute();
    }
}
