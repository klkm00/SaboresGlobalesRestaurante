package com.saboresGlobales.Delivery.delivery.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.saboresGlobales.Delivery.delivery.DTO.DeliveryRequestDTO;
import com.saboresGlobales.Delivery.delivery.DTO.DeliveryResponseDTO;
import com.saboresGlobales.Delivery.delivery.Modelo.Delivery;
import com.saboresGlobales.Delivery.delivery.Repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    private DeliveryResponseDTO mapToDTO(Delivery delivery){
        return new DeliveryResponseDTO(
            delivery.getId(),
            delivery.getRepartidor(),
            delivery.getTarifa(),
            delivery.getGps(),
            delivery.getEstado()
            );
    }

    public List<DeliveryResponseDTO> obtenDeliveryRequestDTOs(){
        return deliveryRepository.findAll()
        .stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
    }

    public Optional<DeliveryResponseDTO> obtenerPorID(Long id){
        return deliveryRepository.findById(id)
        .map(this::mapToDTO);
    }

    public DeliveryResponseDTO guardar(DeliveryRequestDTO dto){
        Delivery delivery = new Delivery(null, dto.getRepartidor(),dto.getTarifa(),dto.getGps(),dto.getEstado());
    return mapToDTO(deliveryRepository.save(delivery));
    }
    public void eliminar(Long id){
        deliveryRepository.deleteById(id);
    }

    public List<DeliveryResponseDTO> buscarporRepartidor(String text){
        return deliveryRepository.findByRepartidorContainingIgnoreCase(text)
        .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}