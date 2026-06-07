package com.saboresGlobales.Pedido.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saboresGlobales.Pedido.DTO.OrdenRequestDTO;
import com.saboresGlobales.Pedido.DTO.OrdenResponseDTO;
import com.saboresGlobales.Pedido.Service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ordenes")
@RequiredArgsConstructor
@Tag(name = "Ordenes", description = "Operaciones relacionadas con las órdenes de pedido")
public class OrdenController {
    private final OrdenService ordenService;

    @GetMapping
    @Operation(summary = "Obtener todas las órdenes", description = "Devuelve una lista de todas las órdenes de pedido")
    public ResponseEntity<List<OrdenResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(ordenService.obtenerTodos());
         
    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una orden por ID", description = "Devuelve los detalles de una orden de pedido específica por su ID")
        public ResponseEntity<OrdenResponseDTO> obtenerporId(@PathVariable Long id){
            return ordenService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }


        
    @PostMapping
    @Operation(summary = "Crear una nueva orden", description = "Crea una nueva orden de pedido con los datos proporcionados")
    public ResponseEntity<OrdenResponseDTO> crear(@RequestBody OrdenRequestDTO dto) {
        OrdenResponseDTO nuevaOrden = ordenService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una orden", description = "Actualiza los datos de una orden de pedido específica por su ID")
    public ResponseEntity<OrdenResponseDTO> actualizar(@PathVariable Long id,
         @RequestBody OrdenResponseDTO dto) {
       
        Optional<OrdenResponseDTO> ordenActualizada = ordenService.actualizar(id, dto);
        return ordenActualizada.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una orden", description = "Elimina una orden de pedido específica por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ordenService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
