package com.example.ms_pagos.controller;

import com.example.ms_pagos.dto.PagoRequestDTO;
import com.example.ms_pagos.dto.PagoResponseDTO;
import com.example.ms_pagos.service.PagoService;
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
public class PagoController {

    private static final Logger log = LoggerFactory.getLogger(PagoController.class);

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // GET /pagos → Listar todos los pagos
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> getAll() {
        log.info("[ms_pagos] GET /pagos - Solicitud de todos los pagos");
        return ResponseEntity.ok(pagoService.getAll());
    }

    // GET /pagos/{id} → Obtener pago por ID
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> getById(@PathVariable Long id) {
        log.info("[ms_pagos] GET /pagos/{} - Solicitud de pago por id", id);
        return ResponseEntity.ok(pagoService.getById(id));
    }

    // GET /pagos/pedido/{pedidoId} → Obtener pagos de un pedido
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<PagoResponseDTO>> getByPedido(@PathVariable Long pedidoId) {
        log.info("[ms_pagos] GET /pagos/pedido/{} - Pagos del pedido", pedidoId);
        return ResponseEntity.ok(pagoService.getByPedidoId(pedidoId));
    }

    // POST /pagos → Procesar un nuevo pago
    @PostMapping
    public ResponseEntity<PagoResponseDTO> procesarPago(@Valid @RequestBody PagoRequestDTO request) {
        log.info("[ms_pagos] POST /pagos - Procesando pago para pedidoId={}", request.getPedidoId());
        PagoResponseDTO resultado = pagoService.procesarPago(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    // PUT /pagos/{id}/anular → Anular un pago confirmado
    @PutMapping("/{id}/anular")
    public ResponseEntity<PagoResponseDTO> anularPago(@PathVariable Long id) {
        log.info("[ms_pagos] PUT /pagos/{}/anular - Solicitud de anulación", id);
        return ResponseEntity.ok(pagoService.anularPago(id));
    }

    // DELETE /pagos/{id} → Eliminar un pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("[ms_pagos] DELETE /pagos/{} - Solicitud de eliminación", id);
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
