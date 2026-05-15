package com.saboresglobales.carrito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.saboresglobales.carrito.model.CarritoModel;
import com.saboresglobales.carrito.service.CarritoService;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {
    @Autowired
    
    private final CarritoService carritoService;

    @PostMapping("/{clienteId}")
    public ResponseEntity<CarritoModel> crearCarrito(@PathVariable UUID clienteId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.crearCarrito(clienteId));
    }

    @GetMapping("/activo/{clienteId}")
    public ResponseEntity<CarritoModel> obtenerCarritoActivo(@PathVariable UUID clienteId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoActivo(clienteId));
    }

    @GetMapping("/{idCarrito}")
    public ResponseEntity<CarritoModel> buscarPorId(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.buscarPorId(idCarrito));
    }

    @PutMapping("/{idCarrito}/confirmar")
    public ResponseEntity<CarritoModel> confirmarCarrito(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.confirmarCarrito(idCarrito));
    }

    @PutMapping("/{idCarrito}/cancelar")
    public ResponseEntity<CarritoModel> cancelarCarrito(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(carritoService.cancelarCarrito(idCarrito));
    }
}