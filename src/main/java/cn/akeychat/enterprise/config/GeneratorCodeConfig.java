package cn.akeychat.enterprise.config;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.*;

/**
 * @Description:    自动生成相关代码
 * @Remark:         备注
 * @Author:         haocheng
 * @Date:           2021-09-14 14:29:21
 */
public class GeneratorCodeConfig {
    
    private final static String PACKAGE = "cn.akeychat.enterprise";
    private final static String CONTROLLER = "controller";
    private final static String ENTITY = "entity";
    private final static String MAPPER = "mapper";
    private final static String SERVICE = "service";
    private final static String SERVICE_IMPL = "service.impl";
    private final static String AUTHOR = "haocheng";
    private final static String MAPPER_XML_PATH = "/src/main/resources/mapper/";
    
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 配置策略
        // 1、全局配置
        GlobalConfig gc = new GlobalConfig();
        // 当前项目的路径
        String projectPath = System.getProperty("user.dir");
        // 生成文件输出根目录
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(AUTHOR);
//        gc.setDateType(DateType.ONLY_DATE);
        gc.setOpen(false);
        //是否开启 Swagger2 注解
        gc.setSwagger2(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        // 2、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
///        dsc.setUrl("jdbc:mysql://10.10.1.129:3306/ansiyuan_new?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true");
        dsc.setUrl("jdbc:postgresql://192.168.2.134:5432/openfire");
        dsc.setDriverName("org.postgresql.Driver");
        dsc.setUsername("postgres");
        dsc.setPassword("viewadmin2014");
        mpg.setDataSource(dsc);

        // 3、包配置
        PackageConfig pc = new PackageConfig();
///        pc.setModuleName(scanner("模块名"));
        pc.setParent(PACKAGE);
        pc.setController(CONTROLLER);
        pc.setEntity(ENTITY);
        pc.setService(SERVICE);
        pc.setServiceImpl(SERVICE_IMPL);
        pc.setMapper(MAPPER);
        mpg.setPackageInfo(pc);

        // 4、自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 1> 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 2> 自定义输出配置,自定义配置会被优先输出
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名，如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + MAPPER_XML_PATH + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        // 3> 如果已存在并且不是实体类则不创建
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                if (new File(filePath).exists() && fileType != FileType.ENTITY) {
                    return false;
                }
                // 判断文件夹是否需要创建
                checkDir(filePath);
                return true;
            }
        });
        mpg.setCfg(cfg);

        // 5、配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController("/templatesFreemarker/controller.java");
        templateConfig.setService("/templatesFreemarker/service.java");
        templateConfig.setServiceImpl("/templatesFreemarker/serviceImpl.java");
        templateConfig.setEntity("/templatesFreemarker/entity.java");
        templateConfig.setMapper("/templatesFreemarker/mapper.java");
///        templateConfig.setXml("/templatesFreemarker/mapper.xml");
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 6、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        //支持lombok
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 表字段注释启动 启动模板中的这个 <#if table.convert>
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 公共父类
///        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
///        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        //逻辑删除属性名称
///        strategy.setLogicDeleteFieldName("data_valid");
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
