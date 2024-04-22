package com.spring.bikesshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui/index.html
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        		.components(new Components())
                .info(new Info()
                        .title("Bikes Shop API")
                        .version("v0.0.1")
                        .description("API REST for Bikes Shop")
                        .license(new License().name("FGA").url("https://github.com/FrankyGA/ApiRESTBikesShop/")));
    }

	/*
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 * registry.addResourceHandler("/swagger-ui/**") .addResourceLocations(
	 * "classpath:/META-INF/resources/webjars/springdoc-openapi-ui/")
	 * .resourceChain(true); }
	 */
}



















/*
 * @Configuration
 * 
 * @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme =
 * "bearer", bearerFormat = "JWT") public class OpenApiConfig implements
 * WebMvcConfigurer {
 * 
 * @Bean public OpenAPI customOpenAPI() { return new OpenAPI() .info(new Info()
 * .title("Bikes Shop API") .version("v0.0.1")
 * .description("API REST for Bikes Shop") .license(new
 * License().name("FGA").url("https://github.com/FrankyGA/ApiRESTBikesShop/")));
 * }
 * 
 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
 * // Configuración para exponer los recursos de Swagger-UI correctamente
 * registry.addResourceHandler("/swagger-ui/**") .addResourceLocations(
 * "classpath:/META-INF/resources/webjars/swagger-ui/3.50.0/")
 * .resourceChain(false); } }
 */