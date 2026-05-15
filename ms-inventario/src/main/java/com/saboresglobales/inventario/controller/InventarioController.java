
package com.saboresglobales.inventario.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.*;
import com.example.ms_inventario.service.*;
import com.example.ms_inventario.model.*;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService service;
    public InventarioController(InventarioService service){this.service=service;}

    @GetMapping public ResponseEntity<List<Inventario>> getAll(){return ResponseEntity.ok(service.getAll());}
    @PostMapping public ResponseEntity<Inventario> create(@RequestBody Inventario i){return ResponseEntity.ok(service.save(i));}
}
