package com.utp.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("API Documentation")
                    .version("1.0")
                    .description("Documentación de la API para la aplicación de Notas")) // Información general de la API
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Añade seguridad JWT a las operaciones
                .components(new Components()
                    .addSecuritySchemes("bearerAuth", // Configura el esquema de seguridad JWT para el header Authorization
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP) // Define el tipo como HTTP
                            .scheme("bearer") // Define el esquema como Bearer Token
                            .bearerFormat("JWT") // Indica que el formato del token es JWT
                            .in(SecurityScheme.In.HEADER) // Los tokens estarán en el header de la solicitud
                            .name("Authorization") // El nombre del header será "Authorization"
                    )
                );
    }
}
