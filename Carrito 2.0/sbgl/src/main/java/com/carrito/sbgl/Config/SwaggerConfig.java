package com.carrito.sbgl.Config;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                    .title("Carrito - Sabores Globales")
                    .version("1.0")
                    .description("API REST para gestionar el carrito en el sistema de Sabores Globales"));
    }
}
