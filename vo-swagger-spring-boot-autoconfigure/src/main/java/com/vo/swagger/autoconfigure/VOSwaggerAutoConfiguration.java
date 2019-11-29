
package com.vo.swagger.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Auto Configuration class for the swagger fox allowing for the addition
 * of swagger fox based on configuration.
 */
@Configuration
@EnableConfigurationProperties(VOSwaggerProperties.class)
@EnableSwagger2
public class VOSwaggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Docket serviceApi(VOSwaggerProperties voSwaggerProperties) {
        Parameter paramWithCorrectDomainNameAsRequestHeader = new ParameterBuilder()
                .name("VO-DOMAIN")
                .description("Domain identifier")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true)
                .build();

        Parameter paramWithCorrectDomainNameAsApiPath = new ParameterBuilder()
                .name("domainId")
                .description("Domain identifier")
                .modelRef(new ModelRef("String"))
                .parameterType("path")
                .required(true)
                .build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        if (voSwaggerProperties.getDomainInHeader()) {
            docket.select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                    .build()
                    .apiInfo(apiInfo(voSwaggerProperties))
                    .useDefaultResponseMessages(false)
                    .globalOperationParameters(Collections.singletonList(paramWithCorrectDomainNameAsRequestHeader));
        } else {
            docket.select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                    .paths(PathSelectors.regex("^/(((d|D)omains)|(site))/.*"))
                    .build()
                    .apiInfo(apiInfo(voSwaggerProperties))
                    .useDefaultResponseMessages(false)
                    .globalOperationParameters(Collections.singletonList(paramWithCorrectDomainNameAsApiPath));
        }
        return docket;
    }

    private ApiInfo apiInfo(VOSwaggerProperties voSwaggerProperties) {
        return new ApiInfoBuilder()
                .title(voSwaggerProperties.getTitle())
                .description(voSwaggerProperties.getDescription())
                .version(voSwaggerProperties.getVersion())
                .build();
    }
}
