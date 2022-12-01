package com.instagram.global.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.Example;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String API_NAME = "인스타그램 API 명세서";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "API 명세서 입니다";

    @Bean
    public Docket api() {

        //전역 파라미터 설정
        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder();
        parameterBuilder
                .name("Access Tocken")
                .description("Access Tocken")
                .query(q -> q.defaultValue("eyJ0easdaxcc2RpWxxw~~")
                        .model(modelSpecificationBuilder -> modelSpecificationBuilder.scalarModel(ScalarType.STRING)))
                .in("header")
                .required(false)
                .build();

        List<RequestParameter> globalParamters = new ArrayList<>();
        globalParamters.add(parameterBuilder.build());

        //API 기본 설정
        return new Docket(DocumentationType.SWAGGER_2)
                .globalRequestParameters(globalParamters)   //전역 파라미터 등록
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.instagram")) // 특정 패키지경로를 API문서화 한다. 1차 필터
                .paths(PathSelectors.any())                                // apis중에서 특정 path조건 API만 문서화 하는 2차 필터
                .build()
                .groupName("API 1.0.0") // group별 명칭을 주어야 한다.
                .pathMapping("/")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false); // 400,404,500 .. 표기를 ui에서 삭제한다.
  }




    // API에 대한 정보
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
//                .termsOfServiceUrl("")
//                .contact()
//                .license("")
//                .licenseUrl("")
                .build();
    }






}