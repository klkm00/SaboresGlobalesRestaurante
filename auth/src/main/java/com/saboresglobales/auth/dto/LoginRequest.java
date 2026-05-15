package com.saboresglobales.auth.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank
    @Email
    private String correo;
    @NotBlank
    private String contrasena;
}