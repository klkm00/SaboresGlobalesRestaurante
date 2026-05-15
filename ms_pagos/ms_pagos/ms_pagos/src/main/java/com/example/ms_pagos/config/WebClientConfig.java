package com.example.ms_pagos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuración del WebClient para comunicarse con ms_pedidos.
 */
@Configuration
public class WebClientConfig {

    @Value("${ms.pedidos.url}")
    private String pedidosUrl;

    @Bean
    public WebClient webClientPedidos(WebClient.Builder builder) {
        return builder
                .baseUrl(pedidosUrl)
                .build();
    }
}
