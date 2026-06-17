package com.saboresglobales.carrito.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saboresglobales.carrito.dto.CarritoRequest;
import com.saboresglobales.carrito.dto.CarritoResponse;
import com.saboresglobales.carrito.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
@Tag(name = "Carrito", description = "Gestión del carrito de compras")
public class CarritoController {

    private final CarritoService carritoService;

    @Operation(summary = "Crear un carrito nuevo para un cliente")
    @ApiResponse(responseCode = "201", description = "Carrito creado correctamente")
    @PostMapping
    public ResponseEntity<CarritoResponse> crearCarrito(@RequestBody @Valid CarritoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.crearCarrito(request));
    }

    @Operation(summary = "Obtener el carrito activo de un cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
        @ApiResponse(responseCode = "400", description = "No hay carrito activo para el cliente")
    })
    @GetMapping("/activo/{clienteId}")
    public ResponseEntity<CarritoResponse> obtenerCarritoActivo(@PathVariable UUID clienteId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoActivo(clienteId));
    }

    @Operation(summary = "Buscar carrito por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
        @ApiResponse(responseCode = "400", description = "Carrito no encontrado")
    })
    @GetMapping("/{idCarrito}")
    public ResponseEntity<CarritoResponse> buscarPorId(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.buscarPorId(idCarrito));
    }

    @Operation(summary = "Confirmar el carrito para ir a pagar")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Carrito confirmado correctamente"),
        @ApiResponse(responseCode = "400", description = "El carrito no está activo")
    })
    @PutMapping("/{idCarrito}/confirmar")
    public ResponseEntity<CarritoResponse> confirmarCarrito(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.confirmarCarrito(idCarrito));
    }

    @Operation(summary = "Cancelar el carrito")
    @ApiResponse(responseCode = "200", description = "Carrito cancelado correctamente")
    @PutMapping("/{idCarrito}/cancelar")
    public ResponseEntity<CarritoResponse> cancelarCarrito(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.cancelarCarrito(idCarrito));
    }
}