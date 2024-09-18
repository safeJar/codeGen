package com.kenny.code.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;

public class Generator {


    public static void main(String[] args) {
        String db_url = args[0];
        String db_username = args[1];
        String db_password = args[2];
        String author = args[3];
        String javaDir = args[4];
        String parentPackage = args[5];
        String tableName = args[6];
        String tablePrefix = args[7];
        Boolean swagger = Boolean.valueOf(args[8]);
        String logicDeleteField = args[9];
        String insertFields = args[10];
        String updateFields = args[11];
        Boolean controller = Boolean.valueOf(args[12]);
        Boolean service = Boolean.valueOf(args[13]);
        Boolean serviceImpl = Boolean.valueOf(args[14]);
        Boolean hasMapper = Boolean.valueOf(args[15]);
        for (String arg : args) {
            System.out.println(arg);
        }

        generatorToFile(db_url, db_username, db_password,
                author, javaDir, parentPackage,
                tableName, tablePrefix, swagger,
                logicDeleteField, insertFields, updateFields,
                controller, service, serviceImpl, hasMapper);
    }

    /**
     * 生成文件
     *
     * @param db_url        db url
     * @param db_username   数据库用户名
     * @param db_password   数据库密码
     * @param author        作者
     * @param javaDir       java dir
     * @param parentPackage 父包
     * @param tableName     表名
     * @param tablePrefix   表前缀
     * @param controller    是否生成 controller
     * @param service       是否生成 service
     * @param serviceImpl   是否生成 serviceImpl
     */
    public static void generatorToFile(String db_url, String db_username, String db_password,
                                       String author, String javaDir, String parentPackage,
                                       String tableName, String tablePrefix, Boolean swagger,
                                       String logicDeleteField, String insertFields, String updateFields,
                                       Boolean controller, Boolean service, Boolean serviceImpl, Boolean hasMapper) {
        FastAutoGenerator generator = FastAutoGenerator.create(db_url, db_username, db_password);

        //===全局配置
        generator.globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .dateType(DateType.ONLY_DATE) // 设置日期为 Date
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(javaDir); // 指定输出目录
                    if (swagger) {
                        builder.enableSwagger();// 开启 swagger 模式
                    }
                }
        );
        //===模块配置
        generator.packageConfig(builder -> {
                    builder.parent(parentPackage) // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, javaDir + "/../resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    //Controller
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    //mapper
                    builder.mapperBuilder().enableMapperAnnotation().build();
                    //实体类
                    Entity.Builder builder2 = builder.entityBuilder().enableLombok();
                    if (logicDeleteField != null && logicDeleteField.trim().length() > 0) {
                        builder2.logicDeleteColumnName(logicDeleteField);
                    }
                    for (String s : insertFields.split(",")) {
                        builder2.addTableFills(new Column(s.trim(), FieldFill.INSERT));
                    }
                    for (String s : updateFields.split(",")) {
                        builder2.addTableFills(new Column(s.trim(), FieldFill.INSERT_UPDATE));
                    }
                    // 设置需要生成的表名
                    for (String s : tableName.split(",")) {
                        builder.addInclude(s.trim());
                    }
                    for (String s : tablePrefix.split(",")) {
                        builder.addTablePrefix(s.trim());
                    }
                })
                .templateEngine(new FreemarkerTemplateEngine())// 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateConfig(builder -> builder.entity("templates/entity.java"));

        if (!controller) {
            generator.templateConfig(builder -> builder.controller("")); // 设置为空则不会生成该文件
        } else {
            generator.templateConfig(builder -> builder.controller("templates/controller.java"));
        }
        if (!service) {
            generator.templateConfig(builder -> builder.service(""));
        } else {
            generator.templateConfig(builder -> builder.service("templates/service.java"));
        }
        if (!serviceImpl) {
            generator.templateConfig(builder -> builder.serviceImpl(""));
        } else {
            generator.templateConfig(builder -> builder.serviceImpl("templates/serviceImpl.java"));
        }
        if (!hasMapper) {
            generator.templateConfig(builder -> builder.serviceImpl(""));
        }
        generator.execute();
    }
}
