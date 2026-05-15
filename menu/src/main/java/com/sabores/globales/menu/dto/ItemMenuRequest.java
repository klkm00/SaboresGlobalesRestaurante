package com.sabores.globales.menu.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemMenuRequest {

    @NotNull
    private UUID idOrigen;      //solo el id del origen
    @NotNull
    private UUID productoId;    //solo el id del producto
    private Boolean itemDisponible;
}