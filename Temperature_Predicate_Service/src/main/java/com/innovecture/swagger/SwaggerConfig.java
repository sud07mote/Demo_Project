package com.innovecture.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

	/**
	 * 
	 * @return
	 */

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.innovecture")).paths(PathSelectors.any()).build()
				.apiInfo(metaData());
	}

	/**
	 * Sets the api's meta information as included in the json ResourceListing
	 * response.
	 * 
	 */
	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Temperature Predicate Service API's")
				.description("\"REST API of Temperature Predicate Service \"").version("1.0.0")
				.contact(new Contact("Team Innovecture", "", "sudarshan121mote@gmail.com")).build();
	}

	@Override
	protected void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}