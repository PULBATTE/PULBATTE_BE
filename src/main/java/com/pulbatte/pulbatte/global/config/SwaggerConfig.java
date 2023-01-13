package com.pulbatte.pulbatte.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private String version = "V0.1";
    @Bean
    public Docket apiBoard(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)         //불필요한 응답코드와 설명 제거
                .groupName("post")                              //Bean이 여러개일 때 명시
                .select()                                        //ApiSelectorBuilder를 생성하여 apis()와 paths()를 사용
                .apis(RequestHandlerSelectors.any())         // api가 작성되있는 패키지를 지정
                /*.apis(RequestHandlerSelectors.                   // api가 작성되있는 패키지를 지정
                        basePackage("com.pulbatte.pulbatte.post.controller"))*/
                .paths(PathSelectors.any())                      //URL 경로를 지정하여 해당 URL에 해당하는 요청만 Swagger API 문서로 만듬
                .build()
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("PULBATTE")
                .description("식집사들을 위한 공간")
                .version(version)
                .build();
    }
}
