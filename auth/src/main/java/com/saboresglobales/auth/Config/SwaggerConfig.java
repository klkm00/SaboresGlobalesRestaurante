package com.saboresglobales.auth.Config;

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
                        .title("API de Autenticación - Sabores Globales Restaurante")
                        .version("1.0")
                        .description("Documentación de la API de autenticación para el sistema de Sabores Globales Restaurante"));
                        


    }                


}
