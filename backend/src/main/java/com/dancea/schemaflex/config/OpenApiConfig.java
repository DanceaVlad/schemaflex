package com.dancea.schemaflex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI schemaFlexOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SchemaFlex API")
                        .version("0.0.1")
                        .description("Dynamic saving and validation of forms based on Json Schemas"));
    }
}
