package com.saboresglobales.auth.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private String rol;
    private String nombre;
    private String correo;
}