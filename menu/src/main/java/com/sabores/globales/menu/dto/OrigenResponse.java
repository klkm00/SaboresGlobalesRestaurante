package com.sabores.globales.menu.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrigenResponse {

    private UUID idOrigen;
    private String nombreCarta;
    private String descripcionCarta;
    private Boolean cartaDisponible;
    
}