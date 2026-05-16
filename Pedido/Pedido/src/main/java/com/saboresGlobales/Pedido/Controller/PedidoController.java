package com.saboresGlobales.Pedido.Controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saboresGlobales.Pedido.DTO.PedidoRequestDTO;
import com.saboresGlobales.Pedido.DTO.PedidoResponseDTO;
import com.saboresGlobales.Pedido.Service.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    @GetMapping

    public ResponseEntity<List<PedidoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
         
    }

    @GetMapping("/{id}")
        public ResponseEntity<PedidoResponseDTO> obtenerporId(@PathVariable Long id){
            return pedidoService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<PedidoResponseDTO>create(@Valid @RequestBody PedidoRequestDTO dto) {
            return ResponseEntity.status(201).body(pedidoService.guardar(dto));
        }
        




    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> actualizar(@PathVariable Long id,
        @Valid @RequestBody PedidoRequestDTO dto) {
            return pedidoService.actualizar(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if(pedidoService.obtenerPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();

        }
        pedidoService.elimnar(id);
        return ResponseEntity.noContent().build();
    }    
    @GetMapping("/buscar/{id}")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorOrden(
        @PathVariable Long id){
            return ResponseEntity.ok(pedidoService.buscarPorOrden(id));
        }


    
}
