package com.example.ms_pagos.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para recibir los datos de un nuevo pago.
 * Separa la validación de la entidad JPA (buena práctica).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

    @NotNull(message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser un número positivo")
    private Long pedidoId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double monto;
    
    @NotNull(message = "El estado del pago es obligatorio")
    private String estado;

    @NotNull(message = "La fecha del pago es obligatoria")
    private LocalDateTime fechaPago;
    
    @NotNull(message = "El método de pago es obligatorio")
    @NotBlank(message = "El método de pago es obligatorio")
    @Pattern(
        regexp = "TARJETA|EFECTIVO|TRANSFERENCIA",
        message = "Método de pago inválido. Use: TARJETA, EFECTIVO o TRANSFERENCIA"
    )
    private String metodoPago;
    @NotNull(message = "El código de transacción es obligatorio")
    @NotBlank(message = "El código de transacción es obligatorio")
    private String codigoTransaccion;

    
}
