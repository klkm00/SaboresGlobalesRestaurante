package com.example.ms_pagos.controller;

import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import com.example.ms_pagos.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST del microservicio de Pagos.
 * Solo orquesta las solicitudes HTTP y delega la lógica al servicio.
 */
@RestController
@RequestMapping("/pagos")
@Tag(name = "Pagos", description = "Operaciones relacionadas con los pagos")
public class PagoController {

    private static final Logger log = LoggerFactory.getLogger(PagoController.class);

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // GET /pagos → Listar todos los pagos
    @GetMapping
    @Operation(summary = "Listar todos los pagos", description = "Obtiene una lista de todos los pagos registrados en el sistema")
    public ResponseEntity<List<PagoResponseDTO>> getAll() {
        log.info("[ms_pagos] GET /pagos - Solicitud de todos los pagos");
        return ResponseEntity.ok(pagoService.getAll());
    }

    // GET /pagos/{id} → Obtener pago por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Obtiene los detalles de un pago específico utilizando su ID")
    public ResponseEntity<PagoResponseDTO> getById(@PathVariable Long id) {
        log.info("[ms_pagos] GET /pagos/{} - Solicitud de pago por id", id);
        return ResponseEntity.ok(pagoService.getById(id));
    }

    // GET /pagos/pedido/{pedidoId} → Obtener pagos de un pedido
    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Obtener pagos de un pedido", description = "Obtiene una lista de pagos asociados a un pedido específico utilizando el ID del pedido")
    public ResponseEntity<List<PagoResponseDTO>> getByPedido(@PathVariable Long pedidoId) {
        log.info("[ms_pagos] GET /pagos/pedido/{} - Pagos del pedido", pedidoId);
        return ResponseEntity.ok(pagoService.getByPedidoId(pedidoId));
    }

    // POST /pagos → Procesar un nuevo pago
    @PostMapping
    @Operation(summary = "Procesar un nuevo pago", description = "Procesa un nuevo pago para un pedido utilizando los detalles proporcionados en el cuerpo de la solicitud")
    public ResponseEntity<PagoResponseDTO> procesarPago(@Valid @RequestBody PagoRequestDTO request) {
        log.info("[ms_pagos] POST /pagos - Procesando pago para pedidoId={}", request.getPedidoId());
        PagoResponseDTO resultado = pagoService.procesarPago(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    // PUT /pagos/{id}/anular → Anular un pago confirmado
    @PutMapping("/{id}/anular")
    @Operation(summary = "Anular un pago confirmado", description = "Anula un pago confirmado utilizando su ID")
    public ResponseEntity<PagoResponseDTO> anularPago(@PathVariable Long id) {
        log.info("[ms_pagos] PUT /pagos/{}/anular - Solicitud de anulación", id);
        return ResponseEntity.ok(pagoService.anularPago(id));
    }

    // DELETE /pagos/{id} → Eliminar un pago
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago", description = "Elimina un pago utilizando su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("[ms_pagos] DELETE /pagos/{} - Solicitud de eliminación", id);
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
