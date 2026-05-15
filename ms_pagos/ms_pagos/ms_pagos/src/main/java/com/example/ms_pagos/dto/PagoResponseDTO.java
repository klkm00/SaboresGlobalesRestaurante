package com.example.ms_pagos.dto;

import com.example.ms_pagos.model.Pago.EstadoPago;
import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO para exponer los datos de un pago en las respuestas REST.
 * No expone campos internos innecesarios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoResponseDTO {

    private Long id;
    private Long pedidoId;
    private Double monto;
    private EstadoPago estado;
    private String metodoPago;
    private LocalDateTime fechaPago;
    private String codigoTransaccion;
}
