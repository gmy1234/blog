package com.gmy.blog.bulid;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CreateCode {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://43.138.22.185:3306/blog", "root", "2000528gmy")
                .globalConfig(builder -> {
                    builder.author("Gmy") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("/Users/gmydl/JavaCode/blog/my-blog/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.gmy") // 设置父包名
                            .moduleName("blog") // 设置父包模块名
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("dao")
                            .xml("mapper")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.mapper, "/Users/gmydl/JavaCode/blog/my-blog/src/main/java/com/gmy/blog/dao")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tb_user_role") // 设置需要生成的表名
                            .addTablePrefix("tb_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
