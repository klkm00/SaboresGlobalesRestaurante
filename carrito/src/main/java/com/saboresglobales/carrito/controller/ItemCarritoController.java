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

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class ItemCarritoController {

    private final ItemCarritoService itemCarritoService;

    @GetMapping("/{idCarrito}/items")
    public ResponseEntity<List<ItemCarritoResponse>> listarItems(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(itemCarritoService.listarItems(idCarrito));
    }

    @PostMapping("/{idCarrito}/items")
    public ResponseEntity<ItemCarritoResponse> agregarItem(
            @PathVariable UUID idCarrito,
            @RequestBody @Valid ItemCarritoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemCarritoService.agregarItem(idCarrito, request));
    }

    @DeleteMapping("/items/{idItemCarrito}")
    public ResponseEntity<Void> quitarItem(@PathVariable UUID idItemCarrito) {
        itemCarritoService.quitarItem(idItemCarrito);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idCarrito}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable UUID idCarrito) {
        itemCarritoService.vaciarCarrito(idCarrito);
        return ResponseEntity.noContent().build();
    }
}