package com.saboresGlobales.Pedido.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

import com.saboresGlobales.Pedido.DTO.OrdenRequestDTO;
import com.saboresGlobales.Pedido.DTO.OrdenResponseDTO;
import com.saboresGlobales.Pedido.Model.Orden;
import com.saboresGlobales.Pedido.Repository.OrdenRepository;

@ExtendWith(MockitoExtension.class)
public class OrdenServiceTest {
    @Mock
    private OrdenRepository ordenRepository;

    @InjectMocks
    private OrdenService ordenService;
    @Test
    void testActualizar() {
    Long id = 2L;
    Orden orden = new Orden();
    orden.setId(2L);
    orden.setOrden("Pizza");
    orden.setDescripcion("Cantidad : 1, Sabor: Napolitana");
    OrdenRequestDTO dto = new OrdenRequestDTO(
        "Pizza",
        "Cantidad : 1, Sabor: Napolitana"
        );

    when(ordenRepository.findById(id)).thenReturn(Optional.of(new Orden()));

    when(ordenRepository.save(any(Orden.class)))
                .thenReturn(orden);
    Optional<OrdenResponseDTO> optional =
        ordenService.actualizar(id, dto);

    assertNotNull(orden);
    assertEquals(id, optional.get().getId());
    assertEquals("Pizza", optional.get().getOrden());
    assertEquals("Cantidad : 1, Sabor: Napolitana", optional.get().getDescripcion());

}
    @Test
    void testEliminar() {
        Long id = 1L;
        
        doNothing().when(ordenRepository).deleteById(id);

        ordenService.eliminar(id);

        verify(ordenRepository,times(1)).deleteById(id);

    }

    @Test
    void testGuardar() {
        OrdenRequestDTO requestDTO = new OrdenRequestDTO(
            "Empanadas", 
            "Cantidad: 10, Sabor:Queso"
        );

        Orden orden = new Orden(
            1L, 
            "Empanadas", 
            "Cantidad: 10, Sabor: Queso"
        );

        when(ordenRepository.save(any(Orden.class)))
                .thenReturn(orden);

        OrdenResponseDTO responseDTO = ordenService.guardar(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Empanadas", responseDTO.getOrden());
    }

    @Test
    void testObtenerPorId() {
        Long id = 1L;
        Orden orden = new Orden(
            id, 
            "Empanadas",
            "Cantidad: 10, Sabor: Queso"
        );

        when(ordenRepository.findById(id)).thenReturn(Optional.of(orden));

        Optional<OrdenResponseDTO> drOptional = ordenService.obtenerPorId(id);

        assertTrue(drOptional.isPresent());
        assertEquals(id, drOptional.get().getId());

    }

    @Test
    void testObtenerTodos() {
         Orden orden = new Orden(
            1L, 
            "Empanadas",
            "Cantidad: 10, Sabor: Queso"
        );
        when(ordenRepository.findAll()).thenReturn(List.of(orden));

        List<OrdenResponseDTO> responseDTOs = ordenService.obtenerTodos();

        assertNotNull(responseDTOs);
        assertEquals(1, responseDTOs.size());
    }
}
