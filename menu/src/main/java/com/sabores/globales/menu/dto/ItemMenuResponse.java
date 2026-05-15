package com.sabores.globales.menu.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemMenuResponse {

    private UUID idItem;
    private UUID productoId;
    private Boolean itemDisponible;
    private OrigenResponse origen;   //incluye el origen completo en la respuesta
}