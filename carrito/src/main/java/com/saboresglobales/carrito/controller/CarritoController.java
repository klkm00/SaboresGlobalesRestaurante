package com.saboresglobales.carrito.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saboresglobales.carrito.dto.CarritoRequest;
import com.saboresglobales.carrito.dto.CarritoResponse;
import com.saboresglobales.carrito.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
@Tag(name = "CarritoController", description = "Controlador para gestionar el carrito de compras")
public class CarritoController {

    private final CarritoService carritoService;

    @PostMapping
    @Operation(summary = "Crear un nuevo carrito de compras", description = "Crea un nuevo carrito de compras para un cliente específico")
    public ResponseEntity<CarritoResponse> crearCarrito(@RequestBody @Valid CarritoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.crearCarrito(request));
    }

    @GetMapping("/activo/{clienteId}")
    @Operation(summary = "Obtener el carrito activo para un cliente", description = "Devuelve el carrito de compras activo para un cliente específico")
    public ResponseEntity<CarritoResponse> obtenerCarritoActivo(@PathVariable UUID clienteId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoActivo(clienteId));
    }

    @GetMapping("/{idCarrito}")
    @Operation(summary = "Buscar carrito por ID", description = "Devuelve la información de un carrito de compras específico por su ID")
    public ResponseEntity<CarritoResponse> buscarPorId(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.buscarPorId(idCarrito));
    }

    @PutMapping("/{idCarrito}/confirmar")
    @Operation(summary = "Confirmar carrito", description = "Confirma un carrito de compras específico por su ID")
    public ResponseEntity<CarritoResponse> confirmarCarrito(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.confirmarCarrito(idCarrito));
    }

    @PutMapping("/{idCarrito}/cancelar")
    @Operation(summary = "Cancelar carrito", description = "Cancela un carrito de compras específico por su ID")
    public ResponseEntity<CarritoResponse> cancelarCarrito(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.cancelarCarrito(idCarrito));
    }
}