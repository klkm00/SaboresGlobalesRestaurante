package com.saboresGlobales.Pedido.Controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saboresGlobales.Pedido.DTO.PedidoRequestDTO;
import com.saboresGlobales.Pedido.DTO.PedidoResponseDTO;
import com.saboresGlobales.Pedido.Service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Devuelve una lista de todos los pedidos registrados en el sistema")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
         
    }

    @GetMapping("/{id}")
        @Operation(summary = "Obtener un pedido por ID", description = "Devuelve los detalles de un pedido específico por su ID")
        public ResponseEntity<PedidoResponseDTO> obtenerporId(@PathVariable Long id){
            return pedidoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }

    @PostMapping
            @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido con los datos proporcionados")
        public ResponseEntity<PedidoResponseDTO>create(@Valid @RequestBody PedidoRequestDTO dto) {
            return ResponseEntity.status(201).body(pedidoService.guardar(dto));
        }
        




    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pedido", description = "Actualiza los datos de un pedido específico por su ID")
    public ResponseEntity<PedidoResponseDTO> actualizar(@PathVariable Long id,
        @Valid @RequestBody PedidoRequestDTO dto) {
            return pedidoService.actualizar(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }
    

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pedido", description = "Elimina un pedido específico por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if(pedidoService.obtenerPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();

        }
        pedidoService.elimnar(id);
        return ResponseEntity.noContent().build();
    }    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar pedidos por orden", description = "Devuelve una lista de pedidos asociados a una orden específica por su ID")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorOrden(
        @RequestParam Long orden){
            return ResponseEntity.ok(pedidoService.buscarPorOrden(orden));
        }


    
}
