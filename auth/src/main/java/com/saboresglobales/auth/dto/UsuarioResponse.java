package com.saboresglobales.auth.dto;


import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {

    private UUID idUsuario;
    private String nombre;
    private String correo;
    private String rol;
    private Boolean activo;
}