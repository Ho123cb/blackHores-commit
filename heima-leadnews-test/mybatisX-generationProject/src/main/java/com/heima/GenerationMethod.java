package com.heima;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

import static com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder.packageConfig;

public class GenerationMethod {
    static String  to = "E:\\FinnHu\\Code\\java\\javaClassStudy\\project\\blackHorseCommit\\code\\heima-leadnews\\heima-leadnews-test\\mybatisX-generationProject\\src\\main\\resources\\generation";
        public static void main(String[] args) {
                FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/leadnews_admin?characterEncoding=utf-8&userSSL=false", "root", "root")
                        .globalConfig(builder -> {
                            builder.author("finnhu") // 设置作者
                                    //.enableSwagger() // 开启 swagger 模式
                                    // 覆盖已生成文件
                                    .outputDir(to); // 指定输出目录
                        })
                        .packageConfig(builder -> {
                            builder.parent("com.heima") // 设置父包名
                                    .moduleName("admin") // 设置父包模块名
                                    .entity("pojos")
                                    .pathInfo(Collections.singletonMap(OutputFile.xml, to)); // 设置mapperXml生成路径
                        })

                        .strategyConfig(builder -> {
                            builder.addInclude(
                                    "ad_article_statistics","ad_channel_label","ad_function","ad_label","ad_menu","ad_recommend_strategy","ad_role","ad_role_auth","ad_strategy_group","ad_user","ad_user_equipment","ad_user_login","ad_user_opertion","ad_user_role","ad_vistor_statistics");// 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                        })
                        .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                        .execute();
    }
}
