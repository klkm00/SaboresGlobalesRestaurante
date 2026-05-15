package com.saboresglobales.carrito.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarritoRequest {

    @NotNull
    private UUID productoId;
    @NotNull
    @Min(1)
    private Integer cantidad;
    @NotNull
    private Double precioUnitario;
}