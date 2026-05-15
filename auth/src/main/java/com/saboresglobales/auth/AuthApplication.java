package com.saboresglobales.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;


//para el problema de generar la contrasena 
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class}) 
//configura todo automáticamente excepto el sistema de login por defecto 
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}