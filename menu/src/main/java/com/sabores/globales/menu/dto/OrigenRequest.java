package com.sabores.globales.menu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrigenRequest {

    @NotBlank
    private String nombreCarta;
    @NotBlank
    private String descripcionCarta;
    
}