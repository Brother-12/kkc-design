package com.kerco.generate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class CodeGenerateApplicationTests {
    /**
     * 生成kkc_member 源码
     */
    @Test
    void contextLoads() {
        FastAutoGenerator.create("jdbc:mysql://192.168.250.20:3306/kkc_member?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("kerco") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("I:\\毕业设计"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.kerco.kkc") // 设置父包名
                            .moduleName("member") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "I:\\毕业设计\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("member_follow,member_user") // 设置需要生成的表名
                            .addTablePrefix("member_")
                            .entityBuilder().idType(IdType.ASSIGN_ID)	// 设置过滤表前缀
                            .serviceBuilder().formatServiceFileName("%sService"); 	//去掉自动生成的I前缀


                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    /**
     * 生成kkc_community 源码
     */
    @Test
    void contextLoads2() {
        FastAutoGenerator.create("jdbc:mysql://192.168.250.20:3306/kkc_community?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("kerco") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("I:\\毕业设计"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.kerco.kkc") // 设置父包名
                            .moduleName("community") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "I:\\毕业设计\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("community_advice,community_article,community_article_comment,community_article_content,community_bulletin,community_message,community_question,community_question_comment,community_question_content,community_category,community_tag") // 设置需要生成的表名
                            .addTablePrefix("community_")
                            .entityBuilder().idType(IdType.ASSIGN_ID)	// 设置过滤表前缀
                            .serviceBuilder().formatServiceFileName("%sService"); 	//去掉自动生成的I前缀


                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
