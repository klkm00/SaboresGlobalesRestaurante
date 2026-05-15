package com.saboresglobales.auth.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @NotBlank
    private String nombre;
    @NotBlank
    @Email
    private String correo;
    @NotBlank
    private String contrasena;
    private String rol = "CLIENTE";   //sera cliente por defecto 
}