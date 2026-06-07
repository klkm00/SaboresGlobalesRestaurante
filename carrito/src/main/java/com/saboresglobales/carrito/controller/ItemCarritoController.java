package com.saboresglobales.carrito.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saboresglobales.carrito.dto.ItemCarritoRequest;
import com.saboresglobales.carrito.dto.ItemCarritoResponse;
import com.saboresglobales.carrito.service.ItemCarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
@Tag(name = "ItemCarritoController", description = "Controlador para gestionar los items del carrito de compras")
public class ItemCarritoController {

    private final ItemCarritoService itemCarritoService;

    @GetMapping("/{idCarrito}/items")
    @Operation(summary = "Listar items del carrito", description = "Devuelve la lista de items de un carrito de compras específico")
    public ResponseEntity<List<ItemCarritoResponse>> listarItems(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(itemCarritoService.listarItems(idCarrito));
    }

    @PostMapping("/{idCarrito}/items")
    @Operation(summary = "Agregar item al carrito", description = "Agrega un nuevo item a un carrito de compras específico")
    public ResponseEntity<ItemCarritoResponse> agregarItem(
            @PathVariable UUID idCarrito,
            @RequestBody @Valid ItemCarritoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemCarritoService.agregarItem(idCarrito, request));
    }

    @DeleteMapping("/items/{idItemCarrito}")
    @Operation(summary = "Quitar item del carrito", description = "Elimina un item específico de un carrito de compras por su ID")
    public ResponseEntity<Void> quitarItem(@PathVariable UUID idItemCarrito) {
        itemCarritoService.quitarItem(idItemCarrito);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idCarrito}/vaciar")
    @Operation(summary = "Vaciar carrito", description = "Elimina todos los items de un carrito de compras específico por su ID")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable UUID idCarrito) {
        itemCarritoService.vaciarCarrito(idCarrito);
        return ResponseEntity.noContent().build();
    }
}