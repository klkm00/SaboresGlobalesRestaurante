package com.example.ms_pagos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.pedidos.url}")
    private String pedidosUrl;

    @Bean
    public WebClient webClientPedidos() {
        return WebClient.builder()
                .baseUrl(pedidosUrl)
                .build();
    }
}


