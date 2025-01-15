package com.jk;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author momomian
 * @date 2021/12/28
 */
public class CurdGenerator {


    private static String author = "cry";
    private static String url = "jdbc:mysql://localhost:3306/store?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String username = "root";
    private static String password = "root";
    //输出目录
    private static String outputDir = System.getProperty("user.dir") + "/src/main/java";
    //父包
    private static String parentPackage = "com.jk";
    //模块名
    private static String moduleName = "";
    //表名
    private static String tableName = "address";
    private static String tablePrefix = "";
    // 表有无创建时间日期字段
    private static boolean existDate = false;

    private static String createDateName = "createDate";
    private static String updateDateName = "updateDate";
    // 全局响应类名
    private static String allResultApiName = "ApiResult";

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder(url, username, password)
            //数据库查询
            .dbQuery(new MySqlQuery())
            //数据库类型转换器
            .typeConvert(new MySqlTypeConvert())
            //数据库关键字处理器
            .keyWordsHandler(new MySqlKeyWordsHandler());
    /**
     * 快速生成器
     */
    private static FastAutoGenerator fastAutoGenerator = FastAutoGenerator.create(DATA_SOURCE_CONFIG);


    public static void main(String[] args) {
        genCode();
    }

    /**
     * 全局配置
     */
    public static void globalConfig() {
        fastAutoGenerator.globalConfig(builder -> {
            // 设置作者
            builder.author(author)
                    // 开启 swagger 模式
                    //.enableSwagger()
                    // 覆盖已生成文件
                    .fileOverride()
                    //禁止生成代码后自动弹出输出目录
                    .disableOpenDir()
                    // 时间策略
                    .dateType(DateType.TIME_PACK)
                    //注释日期，默认值: yyyy-MM-dd
                    .commentDate("yyyy-MM-dd HH:mm:ss")
                    // 指定输出目录
                    .outputDir(outputDir);
        });
    }

    /**
     * 包配置
     */
    public static void packageConfig() {
        fastAutoGenerator.packageConfig(builder -> {
            // 设置父包名
            builder.parent(parentPackage)
                    // 设置父包模块名
                    .moduleName(moduleName)
                    .entity("entity")
                    .service("service")
                    .serviceImpl("service.impl")
                    .mapper("mapper")
                    .xml("mapper.xml")
                    .controller("controller")
                    //设置自定义的文件包名，默认是other，这边取消掉
                    .other("")
                    // 设置mapperXml生成路径
                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "src/main/java/com/jk/mapper"));
        });
    }

    /**
     * 基本模板配置
     */
    public static void templateConfig() {
        fastAutoGenerator.templateConfig((scanner, builder) -> builder
                .entity("/template/entity.java")
                .service("/template/service.java")
                .serviceImpl("/template/serviceImpl.java")
                .mapper("/template/mapper.java")
                .mapperXml("/template/mapper.xml")
                .controller("/template/controller.java")
        );
    }

    /**
     * 自定义模板配置
     */
    public static void injectionConfig() {
        Map<String, String> fileMap = new HashMap<>();

        Map<String, Object> fieldMap = new HashMap<>();

        fastAutoGenerator.injectionConfig(builder -> builder
                //输出文件之前消费者
                .beforeOutputFile((tableInfo, objectMap) -> {
                    String entityName = tableInfo.getEntityName();
                    Map<String, Object> aPackageMap = (Map) objectMap.get("package");
                    //自定义字段，模板变量  直接粗暴加objectMap，因为加入fieldMap会不生效(源码里写的是先把customMap.putAll到objectMap,再执行的这里--哭了)
                    objectMap.put("table_name", entityName.substring(0, 1).toLowerCase() + entityName.substring(1));
                    objectMap.put("model", aPackageMap.get("Parent") + ".model");
                    objectMap.put("bean", entityName.replace("PO", ""));
                    objectMap.put("vo", entityName + "VO");
                    objectMap.put("convert", entityName + "Convert");
                    objectMap.put("dto", entityName + "DTO");
                    objectMap.put("query", entityName + "Query");
                    objectMap.put("common", "com.jk.common");
                    diyConfig(objectMap);

                    //自定义生成文件配置
                    fileMap.put("\\model\\vo\\" + entityName + "VO.java", "/template/vo.java.vm");
                    fileMap.put("\\model\\convert\\" + entityName + "Convert.java", "/template/convert.java.vm");
                    fileMap.put("\\model\\dto\\" + entityName + "DTO.java", "/template/dto.java.vm");
                    fileMap.put("\\model\\query\\" + entityName + "Query.java", "/template/query.java.vm");
                })
                // 自定义属性，模板变量
                .customMap(fieldMap)
                .customFile(fileMap)

        );

    }

    /**
     * 自定义模板变量配置
     * 主要用于生成一些特殊需求
     *
     * @param objectMap
     */
    private static void diyConfig(Map<String, Object> objectMap) {
        //设定entityLombokModel为true,使用lombok
        objectMap.put("entityLombokModel", true);
        //表有无创建时间日期字段
        objectMap.put("existDate", existDate);
        //时间字段set方法定义
        objectMap.put("setCreateDate", "set" + createDateName.substring(0, 1).toUpperCase() + createDateName.substring(1));
        objectMap.put("setUpdateDate", "set" + updateDateName.substring(0, 1).toUpperCase() + updateDateName.substring(1));
        objectMap.put("ApiResult", allResultApiName);
        objectMap.put("baseResultMap", true);
        objectMap.put("baseColumnList", true);
    }


    /**
     * 策略配置
     *
     * @return
     */
    public static void strategyConfig() {

        fastAutoGenerator.strategyConfig(builder -> {
            // 设置需要生成的表名
            builder.addInclude(tableName)

                    // 设置过滤表前缀
                    .addTablePrefix(tablePrefix);
        });
    }

    /**
     * 配置模板引擎
     */
    public static void templateEngine() {
        fastAutoGenerator.templateEngine(new VelocityTemplateEngine() {

            /**
             * 重写输出自定义文件方法，自定义文件输出路径
             */
            @Override
            protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
                String otherPath = getPathInfo(OutputFile.other);
                customFile.forEach((key, value) -> {
                    String fileName = String.format((otherPath + File.separator + "%s"), key);
                    outputFile(new File(fileName), objectMap, value);
                });
            }
        });

    }

    public static void genCode() {
        globalConfig();
        packageConfig();
        templateConfig();
        strategyConfig();
        injectionConfig();
        templateEngine();
        fastAutoGenerator.execute();
    }


}


