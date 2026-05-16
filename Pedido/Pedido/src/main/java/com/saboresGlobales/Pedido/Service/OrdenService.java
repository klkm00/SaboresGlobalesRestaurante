package com.saboresGlobales.Pedido.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.saboresGlobales.Pedido.DTO.OrdenRequestDTO;
import com.saboresGlobales.Pedido.DTO.OrdenResponseDTO;
import com.saboresGlobales.Pedido.Model.Orden;
import com.saboresGlobales.Pedido.Repository.OrdenRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OrdenService {
    private final OrdenRepository ordenRepository;


    public List<OrdenResponseDTO> obtenerTodos() {
        return ordenRepository.findAll().stream()
        .map(orden -> new OrdenResponseDTO(orden.getId(), orden.getOrden(), orden.getDescripcion()))
        .toList();
    }

    public Optional<OrdenResponseDTO> obtenerPorId(Long id) {
        return ordenRepository.findById(id)
        .map(orden -> new OrdenResponseDTO(orden.getId(), orden.getOrden(), orden.getDescripcion()));
    }

    public OrdenResponseDTO guardar(OrdenRequestDTO dto) {
        Orden orden = new Orden();
        orden.setOrden(dto.getOrden());
        orden.setDescripcion(dto.getDescripcion());

        Orden nuevaOrden = ordenRepository.save(orden);
        return new OrdenResponseDTO(nuevaOrden.getId(), nuevaOrden.getOrden(), nuevaOrden.getDescripcion());
           
    }

    public Optional<OrdenResponseDTO> actualizar(Long id, OrdenResponseDTO dto) {
        return ordenRepository.findById(id)
        .map(orden -> {
            orden.setOrden(dto.getOrden());
            orden.setDescripcion(dto.getDescripcion());
            Orden ordenActualizada = ordenRepository.save(orden);
            return new OrdenResponseDTO(ordenActualizada.getId(), ordenActualizada.getOrden(), ordenActualizada.getDescripcion());
        });
    }

    public void eliminar(Long id) {
        ordenRepository.deleteById(id);
    }
}
