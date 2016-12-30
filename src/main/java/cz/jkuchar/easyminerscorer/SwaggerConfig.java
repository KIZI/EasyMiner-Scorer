package cz.jkuchar.easyminerscorer;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.google.common.base.Predicates;

/**
 * Swagger REST API documentation configuration
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *	Inspired by: 
 *  - http://springfox.github.io/springfox/docs/snapshot/
 *  - https://github.com/swagger-api/swagger-core/wiki/Annotations
 *  - http://springfox.github.io/springfox/javadoc/current/
 *	- https://github.com/springfox/springfox/blob/master/docs/transitioning-to-v2.md
 *
 *  - http://localhost:8080/swagger-ui.html
 */


@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig {
	
	/**
	 * Swagger configuration
	 * @return Docket configuration object 
	 */
	@Bean
    public Docket getApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.useDefaultResponseMessages(false)
                .groupName("easyminer-scorer")
                .apiInfo(apiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }
	
	/**
	 * API description 
	 * @return ApiInfo object with predefined description of API
	 */
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("EasyMiner Scorer API")
                .description("RESTful API for EasyMiner Scorer")
                .termsOfServiceUrl("http://www.easyminer.eu")
                .contact("Jaroslav Kuchar <https://github.com/jaroslav-kuchar/>")
                .license("Apache License Version 2.0")
                .licenseUrl("http://www.easyminer.eu/")
                .version("0.3")
                .build();
    }

}
