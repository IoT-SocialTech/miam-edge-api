package com.miam.edgeApi.shared.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("MIAM API")
                        .description("MIAM API")
                        .version("1.0.0")
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("JwtScheme")
                )
                .components(new Components()
                        //JWT
                        .addSecuritySchemes("JwtScheme",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .description("Authorize by a JWT token")
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}
