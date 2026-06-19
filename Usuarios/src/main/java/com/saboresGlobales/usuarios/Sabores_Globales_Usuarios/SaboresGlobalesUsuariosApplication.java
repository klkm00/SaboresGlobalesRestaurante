package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class SaboresGlobalesUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaboresGlobalesUsuariosApplication.class, args);
	}

}
