package com.kakaobank.schoolaggregation.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


//@OpenAPIDefinition(
//    info = @Info(title = "학교 집계 서비스 API 명세서",
//        description = "학교 집계 서비스 API 명세서",
//        version = "v1"))
//@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {


//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//            .title("Kakaobank-SchoolAggregation-API")
//            .description("Kakaobank-SchoolAggregation API Docs")
//            .version("0.0.1-dev")
//            .build();
//    }
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//            .useDefaultResponseMessages(true)
//            .apiInfo(apiInfo())
//            .select()
//            .apis(RequestHandlerSelectors.any())
//            .paths(PathSelectors.any())
//            .build();
//    }
//
//    @Bean
//    public GroupedOpenApi chatOpenApi() {
//
//        return GroupedOpenApi.builder()
//            .group("학교 집계 서비스 API v1")
//            .packagesToScan("com.kakaobank.schoolaggregation.**")
//            .build();
//    }

}