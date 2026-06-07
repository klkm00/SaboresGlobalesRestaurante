
package com.example.ms_inventario.service;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import com.example.ms_inventario.repository.*;


import lombok.RequiredArgsConstructor;

import com.example.ms_inventario.dto.InventarioRequestDTO;
import com.example.ms_inventario.dto.InventarioResponseDTO;
import com.example.ms_inventario.model.*;

@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository repo;
    
    private InventarioResponseDTO maptoDTO(Inventario inventario){
        return new InventarioResponseDTO(
            inventario.getId(), 
            inventario.getInsumo(), 
            inventario.getStock());
    }

    public List<InventarioResponseDTO> ObtenerTodos(){
        return repo.findAll().stream().map(this::maptoDTO).toList();
    }

    public Optional<InventarioResponseDTO> ObtenerPorId(Long id){
        return repo.findById(id).map(this::maptoDTO);
    }

    public List<InventarioResponseDTO> ObtenerPorInsumo(String insumo){
        return repo.findByInsumo(insumo).stream().map(this::maptoDTO).collect(Collectors.toList());
    }

    public InventarioResponseDTO Guardar(InventarioRequestDTO dto){
       Inventario nuevoInventario = new Inventario();
       nuevoInventario.setInsumo(dto.getInsumo());
       nuevoInventario.setStock(dto.getStock()); 
        return maptoDTO(repo.save(nuevoInventario));
    }

    public Optional<InventarioResponseDTO> Actualizar(Long id, InventarioRequestDTO dto){
        return repo.findById(id).map(inventarioExistente -> {
            inventarioExistente.setInsumo(dto.getInsumo());
            inventarioExistente.setStock(dto.getStock());
            return maptoDTO(repo.save(inventarioExistente));
        });
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }


}
