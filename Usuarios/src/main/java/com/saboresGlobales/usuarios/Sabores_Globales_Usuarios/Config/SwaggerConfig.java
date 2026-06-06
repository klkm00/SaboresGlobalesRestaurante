package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {    
    
    @Bean 
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Usuarios y Roles API")
                        .version("1.0")
                        .description("API para la gestión de usuarios en Sabores Globales"));
    }
}
