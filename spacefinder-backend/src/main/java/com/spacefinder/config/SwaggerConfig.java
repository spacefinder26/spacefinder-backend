package com.spacefinder.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI spacefinderOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Space Finder API")
                        .description("Backend REST API for the Space Finder property application. " +
                                "Use this documentation to integrate the frontend with all available endpoints.")
                        .version("v1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                        .servers(List.of(
                                new Server()
                                        .url("http://localhost:8081")
                                        .description("Local Development Server"),
                                new Server()
                                        .url("https://api.spacefinder.com")
                                        .description("Production Server (Railway)")
                ));
    }
}
