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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ordenes")
@RequiredArgsConstructor
public class OrdenController {
    private final OrdenService ordenService;

    @GetMapping
    public ResponseEntity<List<OrdenResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(ordenService.obtenerTodos());
         
    }
    @GetMapping("/{id}")
        public ResponseEntity<OrdenResponseDTO> obtenerporId(@PathVariable Long id){
            return ordenService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }


        
    @PostMapping
    public ResponseEntity<OrdenResponseDTO> crear(@RequestBody OrdenRequestDTO dto) {
        OrdenResponseDTO nuevaOrden = ordenService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> actualizar(@PathVariable Long id,
         @RequestBody OrdenResponseDTO dto) {
       
        Optional<OrdenResponseDTO> ordenActualizada = ordenService.actualizar(id, dto);
        return ordenActualizada.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ordenService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
