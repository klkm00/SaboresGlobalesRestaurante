package com.saboresglobales.carrito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saboresglobales.carrito.model.ItemCarritoModel;
import com.saboresglobales.carrito.service.ItemCarritoService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class ItemCarritoController {
    @Autowired
    
    private final ItemCarritoService itemCarritoService;

    @GetMapping("/{idCarrito}/items")
    public ResponseEntity<List<ItemCarritoModel>> listarItems(@PathVariable UUID idCarrito) {
        return ResponseEntity.ok(itemCarritoService.listarItems(idCarrito));
    }

    @PostMapping("/{idCarrito}/items")
    public ResponseEntity<ItemCarritoModel> agregarItem(
            @PathVariable UUID idCarrito,
            @RequestParam UUID productoId,
            @RequestParam Integer cantidad,
            @RequestParam Double precioUnitario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemCarritoService.agregarItem(idCarrito, productoId, cantidad, precioUnitario));
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