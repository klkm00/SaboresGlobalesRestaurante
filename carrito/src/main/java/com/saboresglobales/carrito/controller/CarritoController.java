package com.saboresglobales.carrito.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saboresglobales.carrito.dto.CarritoRequest;
import com.saboresglobales.carrito.dto.CarritoResponse;
import com.saboresglobales.carrito.service.CarritoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @PostMapping
    public ResponseEntity<CarritoResponse> crearCarrito(@RequestBody @Valid CarritoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.crearCarrito(request));
    }

    @GetMapping("/activo/{clienteId}")
    public ResponseEntity<CarritoResponse> obtenerCarritoActivo(@PathVariable UUID clienteId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoActivo(clienteId));
    }

    @GetMapping("/{idCarrito}")
    public ResponseEntity<CarritoResponse> buscarPorId(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.buscarPorId(idCarrito));
    }

    @PutMapping("/{idCarrito}/confirmar")
    public ResponseEntity<CarritoResponse> confirmarCarrito(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.confirmarCarrito(idCarrito));
    }

    @PutMapping("/{idCarrito}/cancelar")
    public ResponseEntity<CarritoResponse> cancelarCarrito(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.cancelarCarrito(idCarrito));
    }
}