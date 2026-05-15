package com.saboresglobales.carrito.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoRequest {

    @NotNull
    private UUID clienteId;
}