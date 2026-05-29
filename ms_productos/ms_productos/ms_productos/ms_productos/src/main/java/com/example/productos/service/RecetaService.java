package com.example.productos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.productos.dto.RecetaRequestDTO;
import com.example.productos.dto.RecetaResponseDTO;
import com.example.productos.model.Producto;
import com.example.productos.model.Receta;
import com.example.productos.repository.ProductoRepository;
import com.example.productos.repository.RecetaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecetaService {
    private final RecetaRepository recetaRepo;
    private final ProductoRepository productoRepo;

    private RecetaResponseDTO mapToDto(Receta receta){
        String productoNombre = null;
        if(receta.getProducto() != null){
            productoNombre = receta.getProducto().getNombre();
        }
        return new RecetaResponseDTO(
            receta.getId(),
            receta.getNombreInsumo(),
            receta.getCantidad(),
            productoNombre
        );
    }


    public List<RecetaResponseDTO> obtenerTodas() {
        return recetaRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    public RecetaResponseDTO guardar(RecetaRequestDTO dto){
        Producto producto = productoRepo.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        Receta receta = new Receta(
            null,
            dto.getNombreInsumo(),
            dto.getCantidad(),
            producto
        );
        return mapToDto(recetaRepo.save(receta));
    }
    public Optional<RecetaResponseDTO> obtenerPorId(Long id) {
        return recetaRepo.findById(id).map(this::mapToDto);
    }

    public Optional<RecetaResponseDTO> actualizar(Long id, RecetaRequestDTO dto) {
        return recetaRepo.findById(id).map(receta -> {
            Producto producto = productoRepo.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            receta.setNombreInsumo(dto.getNombreInsumo());
            receta.setCantidad(dto.getCantidad());
            receta.setProducto(producto);
            return mapToDto(recetaRepo.save(receta));
        });
    }

    public void eliminar(Long id) {
        recetaRepo.deleteById(id);
    }

    public List<RecetaResponseDTO> obtenerRecetasPorNombreInsumo(String nombreInsumo) {
        return recetaRepo.findByNombreInsumoContainsIgnoreCase(nombreInsumo).stream()
                .map(this::mapToDto)
                .collect(java.util.stream.Collectors.toList());
    }
    public List<RecetaResponseDTO> obtenerRecetasPorProductoId(Long productoId) {
        return recetaRepo.findByProductoId(productoId).stream()
                .map(this::mapToDto)
                .collect(java.util.stream.Collectors.toList());

    }
}
