package com.saboresglobales.auth.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecuperarContrasenaRequest {

    @NotBlank
    private String codigo;
    @NotBlank
    private String nuevaContrasena;
}