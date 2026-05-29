
package com.example.productos.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.productos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;

import com.example.productos.dto.ProductoRequestDTO;
import com.example.productos.dto.ProductoResponseDTO;
import com.example.productos.model.Producto;
@RequiredArgsConstructor
@Service
public class ProductoService {

    private final ProductoRepository productoRepo;
    

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepo.findByNombreContainsIgnoreCase(nombre);
    }
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepo.findAll().stream()
                .map(producto -> new ProductoResponseDTO(producto.getId(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio()))
                .collect(java.util.stream.Collectors.toList());
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
        return productoRepo.findById(productoId).map(producto -> new ProductoResponseDTO(producto.getId(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio()));
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


}
