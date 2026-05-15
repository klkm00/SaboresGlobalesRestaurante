package com.example.ms_pagos.repository;

import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.model.Pago.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio JPA para la entidad Pago.
 * Extiende JpaRepository para operaciones CRUD automáticas.
 */
public interface PagoRepository extends JpaRepository<Pago, Long> {

    // Buscar todos los pagos de un pedido específico
    List<Pago> findByPedidoId(Long pedidoId);

    // Buscar pagos por estado (PENDIENTE, CONFIRMADO, FALLIDO)
    List<Pago> findByEstado(EstadoPago estado);

    // Verificar si un pedido ya tiene un pago confirmado
    boolean existsByPedidoIdAndEstado(Long pedidoId, EstadoPago estado);
}
