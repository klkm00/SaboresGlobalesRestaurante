package com.saboresGlobales.Delivery.delivery.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.saboresGlobales.Delivery.delivery.DTO.DeliveryRequestDTO;
import com.saboresGlobales.Delivery.delivery.DTO.DeliveryResponseDTO;
import com.saboresGlobales.Delivery.delivery.Modelo.Delivery;
import com.saboresGlobales.Delivery.delivery.Repository.DeliveryRepository;
@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {
    @Mock
    private DeliveryRepository deliveryRepository;
    @InjectMocks
    private DeliveryService  deliveryService;

    @Test
    void testActualizar() {
        Long id = 1L;

        Delivery delivery = new Delivery(
            id, 
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        DeliveryRequestDTO  dto = new DeliveryRequestDTO(
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        when(deliveryRepository.findById(id)).thenReturn(Optional.of(new Delivery()));

        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);

        Optional<DeliveryResponseDTO> optional = deliveryService.actualizar(id, dto);

        assertNotNull(optional);
        assertEquals(id, optional.get().getId());
    }

    @Test
    void testBuscarporRepartidor() {
        String repartidor = "Jupiter";

        Delivery delivery = new Delivery(
            1L, 
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        when(deliveryRepository.findByRepartidorContainingIgnoreCase(repartidor)).thenReturn(List.of(delivery));

        List<DeliveryResponseDTO> deliveryResponseDTOs = deliveryService.buscarporRepartidor(repartidor);

        assertNotNull(deliveryResponseDTOs);
        assertEquals(1, deliveryResponseDTOs.size());
        assertEquals("Jupiter", deliveryResponseDTOs.get(0).getRepartidor());

    }

    @Test
    void testEliminar() {
        Long id = 1L;

        doNothing().when(deliveryRepository).deleteById(id);

        deliveryService.eliminar(id);

        verify(deliveryRepository, times(1)).deleteById(id);
    }

    @Test
    void testGuardar() {
         Delivery delivery = new Delivery(
            1L, 
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        DeliveryRequestDTO  dto = new DeliveryRequestDTO(
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);

        DeliveryResponseDTO dto2 = deliveryService.guardar(dto);
        assertNotNull(dto2);
        assertEquals(1L, dto2.getId());
    }

    @Test
    void testObtenDeliveryRequestDTOs() {
        Delivery delivery = new Delivery(
            1L, 
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        when(deliveryRepository.findAll()).thenReturn(List.of(delivery));

        List<DeliveryResponseDTO> responseDTOs = deliveryService.obtenDeliveryRequestDTOs();

        assertNotNull(responseDTOs);
        assertEquals(1, responseDTOs.size());
        assertEquals(1L, responseDTOs.get(0).getId());

    }

    @Test
    void testObtenerPorID() {
        Long id = 1L;
        Delivery delivery = new Delivery(
            1L, 
            "Jupiter", 
            1500, 
            "Av.Estrella #12", 
            "Entregado"
        );

        when(deliveryRepository.findById(id)).thenReturn(Optional.of(delivery));
        
        Optional<DeliveryResponseDTO> optional = deliveryService.obtenerPorID(id);

        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getId());
        
    }
}
