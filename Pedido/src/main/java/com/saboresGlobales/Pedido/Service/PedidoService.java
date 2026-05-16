package com.saboresGlobales.Pedido.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.saboresGlobales.Pedido.DTO.PedidoRequestDTO;
import com.saboresGlobales.Pedido.DTO.PedidoResponseDTO;
import com.saboresGlobales.Pedido.Model.Orden;
import com.saboresGlobales.Pedido.Model.Pedido;
import com.saboresGlobales.Pedido.Repository.OrdenRepository;
import com.saboresGlobales.Pedido.Repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PedidoService {


    private final PedidoRepository pedidoRepository;
    private final OrdenRepository ordenRepository;

    private PedidoResponseDTO mapToDTO(Pedido pedido){
        String ordenNombre = null;
        if(pedido.getOrden() != null){
            ordenNombre = pedido.getOrden().getOrden();
        }
        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getEstado(),
            ordenNombre
        );
    }

    public List<PedidoResponseDTO> obtenerTodos(){
        return pedidoRepository.findAll()
        .stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
    }
    public Optional<PedidoResponseDTO> obtenerPorId(Long id){
        return pedidoRepository.findById(id).map(this :: mapToDTO);
    }

    public PedidoResponseDTO guardar(PedidoRequestDTO dto){
        Orden orden = ordenRepository
                        .findById(dto.getOrdenId())
                        .orElseThrow(()-> new RuntimeException(
                            "ORDEN NO ENCONTRADA CON ID" + dto.getOrdenId()
                        ));

        Pedido pedido = new Pedido(
            null,
            dto.getEstado(),
            orden
        );
        return mapToDTO(pedidoRepository.save(pedido));
    }


    public Optional<PedidoResponseDTO> actualizar(Long id, PedidoRequestDTO dto){
        return pedidoRepository.findById(id).map(existente ->{
            Orden orden = ordenRepository
            .findById(dto.getOrdenId()).orElseThrow(() -> new RuntimeException("ROL NO ENCONTRADO CON ID"+ dto.getOrdenId()));
            existente.setEstado(dto.getEstado());
            existente.setOrden(orden);
            return mapToDTO(pedidoRepository.save(existente));
        });
    }

    public void elimnar(Long id){
        pedidoRepository.deleteById(id);
    }

    public List<PedidoResponseDTO> buscarPorOrden(Long ordenId){
        return pedidoRepository.findByOrdenId(ordenId)
        .stream().map(this:: mapToDTO).collect(Collectors.toList());
    }
}