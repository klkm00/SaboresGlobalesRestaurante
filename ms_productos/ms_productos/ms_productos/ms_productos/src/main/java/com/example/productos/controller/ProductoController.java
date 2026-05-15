
package com.example.productos.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.example.productos.service.ProductoService;
import com.example.productos.model.Producto;
import com.example.productos.model.Receta;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto p) {
        return ResponseEntity.ok(service.crear(p));
    }

    @GetMapping("/{id}/receta")
    public ResponseEntity<List<Receta>> receta(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerReceta(id));
    }
}
