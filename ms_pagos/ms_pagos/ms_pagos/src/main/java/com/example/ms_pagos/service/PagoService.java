package com.example.ms_pagos.service;

import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import com.example.ms_pagos.exception.RecursoNoEncontradoException;
import com.example.ms_pagos.exception.ReglaNegocioException;
import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.model.Pago.EstadoPago;
import com.example.ms_pagos.repository.PagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio de lógica de negocio para el microservicio de Pagos.
 * Gestiona el procesamiento, consulta y anulación de pagos.
 */
@Service
public class PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;
    private final WebClient webClientPedidos;

    public PagoService(PagoRepository pagoRepository, WebClient webClientPedidos) {
        this.pagoRepository = pagoRepository;
        this.webClientPedidos = webClientPedidos;
    }

    // ---- Obtener todos los pagos ----
    public List<PagoResponseDTO> getAll() {
        log.info("[ms_pagos] Consultando todos los pagos");
        return pagoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ---- Obtener pago por ID ----
    public PagoResponseDTO getById(Long id) {
        log.info("[ms_pagos] Buscando pago con id={}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado con id: " + id));
        return toDTO(pago);
    }

    // ---- Obtener pagos por pedido ----
    public List<PagoResponseDTO> getByPedidoId(Long pedidoId) {
        log.info("[ms_pagos] Consultando pagos del pedido id={}", pedidoId);
        return pagoRepository.findByPedidoId(pedidoId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ---- Procesar un nuevo pago ----
    public PagoResponseDTO procesarPago(PagoRequestDTO request) {
        log.info("[ms_pagos] Procesando pago para pedidoId={}, monto={}", request.getPedidoId(), request.getMonto());

        // Regla de negocio: no se puede pagar dos veces el mismo pedido
        if (pagoRepository.existsByPedidoIdAndEstado(request.getPedidoId(), EstadoPago.CONFIRMADO)) {
            log.warn("[ms_pagos] El pedido {} ya tiene un pago CONFIRMADO", request.getPedidoId());
            throw new ReglaNegocioException("El pedido " + request.getPedidoId() + " ya fue pagado exitosamente");
        }

        // Verificar que el pedido existe consultando ms_pedidos
        verificarPedidoExiste(request.getPedidoId());

        // Simulación del procesamiento con el banco
        boolean pagoExitoso = simularProcesoBancario(request.getMonto());

        Pago pago = Pago.builder()
                .pedidoId(request.getPedidoId())
                .monto(request.getMonto())
                .metodoPago(request.getMetodoPago())
                .estado(pagoExitoso ? EstadoPago.CONFIRMADO : EstadoPago.FALLIDO)
                .fechaPago(LocalDateTime.now())
                .codigoTransaccion(pagoExitoso ? generarCodigoTransaccion() : null)
                .build();

        Pago guardado = pagoRepository.save(pago);
        log.info("[ms_pagos] Pago guardado con id={}, estado={}", guardado.getId(), guardado.getEstado());

        return toDTO(guardado);
    }

    // ---- Anular un pago (cambia estado a FALLIDO) ----
    public PagoResponseDTO anularPago(Long id) {
        log.info("[ms_pagos] Anulando pago id={}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado con id: " + id));

        // Regla de negocio: solo se pueden anular pagos CONFIRMADOS
        if (pago.getEstado() != EstadoPago.CONFIRMADO) {
            log.warn("[ms_pagos] Intento de anular pago en estado {}", pago.getEstado());
            throw new ReglaNegocioException("Solo se pueden anular pagos en estado CONFIRMADO");
        }

        pago.setEstado(EstadoPago.FALLIDO);
        Pago actualizado = pagoRepository.save(pago);
        log.info("[ms_pagos] Pago id={} anulado exitosamente", id);

        return toDTO(actualizado);
    }

    // ---- Eliminar un pago ----
    public void eliminar(Long id) {
        log.info("[ms_pagos] Eliminando pago id={}", id);
        if (!pagoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Pago no encontrado con id: " + id);
        }
        pagoRepository.deleteById(id);
        log.info("[ms_pagos] Pago id={} eliminado", id);
    }

    // ---- Comunicación con ms_pedidos (WebClient) ----
    private void verificarPedidoExiste(Long pedidoId) {
        try {
            log.info("[ms_pagos] Verificando existencia del pedido id={} en ms_pedidos", pedidoId);
            webClientPedidos.get()
                    .uri("/pedidos/{id}", pedidoId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            log.info("[ms_pagos] Pedido id={} verificado correctamente", pedidoId);
        } catch (Exception ex) {
            log.error("[ms_pagos] No se pudo verificar el pedido id={}: {}", pedidoId, ex.getMessage());
            throw new ReglaNegocioException("No se pudo verificar el pedido con id: " + pedidoId + ". Asegúrese que el pedido existe.");
        }
    }

    // ---- Simulación del proceso bancario ----
    private boolean simularProcesoBancario(Double monto) {
        // En un sistema real, aquí se llamaría a la pasarela de pago
        // Para la demo, montos menores a 0.01 fallan
        return monto >= 0.01;
    }

    // ---- Generar código de transacción único ----
    private String generarCodigoTransaccion() {
        return "TXN-" + UUID.randomUUID().toString().toUpperCase().substring(0, 12);
    }

    // ---- Mapear Pago → PagoResponseDTO ----
    private PagoResponseDTO toDTO(Pago pago) {
        return PagoResponseDTO.builder()
                .id(pago.getId())
                .pedidoId(pago.getPedidoId())
                .monto(pago.getMonto())
                .estado(pago.getEstado())
                .metodoPago(pago.getMetodoPago())
                .fechaPago(pago.getFechaPago())
                .codigoTransaccion(pago.getCodigoTransaccion())
                .build();
    }
}
