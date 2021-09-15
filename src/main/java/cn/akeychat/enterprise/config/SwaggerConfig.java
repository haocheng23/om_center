package cn.akeychat.enterprise.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {
    
    @Value("${package.parent}")
    private String packageName;
    
    /**
     * 创建API应用
     * @author     haocheng 
     * @date       2021-09-14 19:20:01
     * @return     Docket
     * @remark     apiInfo() 增加API相关信息
     *             通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现
     *             也可指定扫描的包路径来定义指定要建立API的目录
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                //设置选择器，选择带Api接口类的类
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //api包扫描
                .apis(RequestHandlerSelectors.basePackage(packageName))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes());
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * @author     haocheng 
     * @date       2021-09-14 19:19:17
     * @return     ApiInfo
     * @remark     访问地址：http://ip:端口/swagger-ui.html
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("demo项目")
                .description("demo项目API文档")
                .termsOfServiceUrl("http://localhost")
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList= new ArrayList<>();
        //apiKeyList.add(new ApiKey("token", "令牌", "header"));
        return apiKeyList;
    }
}
