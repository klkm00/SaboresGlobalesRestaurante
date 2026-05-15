package com.saboresglobales.carrito.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoResponse {

    private UUID idCarrito;
    private UUID clienteId;
    private String estado;
    private Double total;
    private LocalDateTime fechaCreado;
    private LocalDateTime fechaActualizado;
    private List<ItemCarritoResponse> items;  //incluye los items del carrito
}