package com.saboresglobales.carrito.dto;

import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarritoResponse {

    private UUID idItemCarrito;
    private UUID productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}