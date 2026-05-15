package com.example.ms_pagos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un pago en el sistema.
 * Cada pago está asociado a un pedido del microservicio ms_pedidos.
 */
@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del pedido en ms_pedidos (clave foránea lógica entre microservicios)
    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    @Column(nullable = false)
    private Double monto;

    // Estado del pago: PENDIENTE, CONFIRMADO, FALLIDO
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    // Método de pago: TARJETA, EFECTIVO, TRANSFERENCIA
    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    // Código de transacción devuelto por el banco (puede ser null si falló)
    @Column(name = "codigo_transaccion", length = 100)
    private String codigoTransaccion;

    public enum EstadoPago {
        PENDIENTE, CONFIRMADO, FALLIDO
    }
}
