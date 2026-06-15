package com.productos.productos.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.productos.productos.DTO.ProductoRequestDTO;
import com.productos.productos.DTO.ProductoResponseDTO;
import com.productos.productos.Modelo.Producto;
import com.productos.productos.Repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepo;
    
    private ProductoResponseDTO mapToDto(Producto producto){
      
        return new ProductoResponseDTO(
            producto.getId(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio()
        );
    }
   
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepo.findAll().stream()
                .map(this::mapToDto).collect(Collectors.toList());
                
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        Producto guardado = productoRepo.save(producto);
        return new ProductoResponseDTO(guardado.getId(), guardado.getNombre(), guardado.getDescripcion(), guardado.getPrecio());
    }

    public Optional<ProductoResponseDTO> obtenerPorId(Long productoId) {
        return productoRepo.findById(productoId).map(this::mapToDto);
    }
    public void eliminar(Long id) {
        productoRepo.deleteById(id);
    }

    public Optional<ProductoResponseDTO> actualizar(Long id, ProductoRequestDTO dto) {
        return productoRepo.findById(id).map(producto -> {
            producto.setNombre(dto.getNombre());
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            Producto actualizado = productoRepo.save(producto);
            return new ProductoResponseDTO(actualizado.getId(), actualizado.getNombre(), actualizado.getDescripcion(), actualizado.getPrecio());
        });
    }

     public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        return productoRepo.findByNombreContainingIgnoreCase(nombre)
        .stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
