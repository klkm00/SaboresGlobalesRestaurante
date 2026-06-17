package com.saboresglobales.carrito.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saboresglobales.carrito.dto.ItemCarritoRequest;
import com.saboresglobales.carrito.dto.ItemCarritoResponse;
import com.saboresglobales.carrito.service.ItemCarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
@Tag(name = "Item Carrito", description = "Gestión de items dentro del carrito")
public class ItemCarritoController {

    private final ItemCarritoService itemCarritoService;

    @Operation(summary = "Listar todos los items de un carrito")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping("/{idCarrito}/items")
    public ResponseEntity<List<ItemCarritoResponse>> listarItems(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(itemCarritoService.listarItems(idCarrito));
    }

    @Operation(summary = "Agregar un producto al carrito")
    @ApiResponse(responseCode = "201", description = "Producto agregado correctamente")
    @PostMapping("/{idCarrito}/items")
    public ResponseEntity<ItemCarritoResponse> agregarItem(
            @PathVariable UUID idCarrito,
            @RequestBody @Valid ItemCarritoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemCarritoService.agregarItem(idCarrito, request));
    }

    @Operation(summary = "Quitar un item del carrito")
    @ApiResponse(responseCode = "204", description = "Item eliminado correctamente")
    @DeleteMapping("/items/{idItemCarrito}")
    public ResponseEntity<Void> quitarItem(@PathVariable UUID idItemCarrito) {
        itemCarritoService.quitarItem(idItemCarrito);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Vaciar todos los items del carrito")
    @ApiResponse(responseCode = "204", description = "Carrito vaciado correctamente")
    @DeleteMapping("/{idCarrito}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable UUID idCarrito) {
        itemCarritoService.vaciarCarrito(idCarrito);
        return ResponseEntity.noContent().build();
    }
}