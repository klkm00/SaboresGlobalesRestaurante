package com.saboresGlobales.Pedido.Service;

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

import com.saboresGlobales.Pedido.DTO.PedidoRequestDTO;
import com.saboresGlobales.Pedido.DTO.PedidoResponseDTO;
import com.saboresGlobales.Pedido.Model.Orden;
import com.saboresGlobales.Pedido.Model.Pedido;
import com.saboresGlobales.Pedido.Repository.OrdenRepository;
import com.saboresGlobales.Pedido.Repository.PedidoRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    @Mock
    private PedidoRepository repository;
    @InjectMocks
    private PedidoService service;
    @Mock
    private OrdenRepository ordenRepository;

    @Test
    void testActualizar() {
        Long id = 2L;
        Orden orden = new Orden();
        orden.setId(1L);
        orden.setOrden("Empanadas");
        Pedido pedido = new Pedido(
            id, 
            "En Preparacion", 
            orden
        );

        PedidoRequestDTO dto = new PedidoRequestDTO(
            "En Preparacion", 
            id
        );

        when(repository.findById(2L))
                .thenReturn(Optional.of(pedido));
        when(ordenRepository.findById(2L))
                .thenReturn(Optional.of(orden));


        when(repository.save(any(Pedido.class))).thenReturn(pedido);

        Optional<PedidoResponseDTO> optional = service.actualizar(id, dto);

        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getId());
        assertEquals("En Preparacion", optional.get().getEstado());
    }

    @Test
    void testBuscarPorOrden() {
        Orden orden = new Orden();
        orden.setId(1L);

        Pedido pedido = new Pedido(
            1L, 
            "Listo", 
            orden
        );

    when(repository.findByOrdenId(1L)).thenReturn(List.of(pedido));

    List<PedidoResponseDTO> responseDTOs = service.buscarPorOrden(1L);
    assertEquals(1, responseDTOs.size());
    assertEquals(1L, responseDTOs.get(0).getId());
    }

    @Test
    void testElimnar() {
        Long id = 1L;

        doNothing().when(repository).deleteById(id);

        service.elimnar(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void testGuardar() {
        Orden orden = new Orden();
        orden.setId(1L);
        orden.setOrden("Empanadas");

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO(
            "Listo", 
            1L
        );


        Pedido pedido = new Pedido(
            1L, 
            "Listo", 
            orden
        );

        when(repository.save(any(Pedido.class))).thenReturn(pedido);


        when(ordenRepository.findById(1L))
                .thenReturn(Optional.of(orden));
        PedidoResponseDTO responseDTO = service.guardar(pedidoRequestDTO);
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
    }

    @Test
    void testObtenerPorId() {
        Long id = 1L;
        Orden orden = new Orden();
        orden.setId(1L);
        orden.setOrden("Empanadas");
        Pedido pedido = new Pedido(
            1L, 
            "Listo", 
            orden
        );

        when(repository.findById(id)).thenReturn(Optional.of(pedido));

        Optional<PedidoResponseDTO> o = service.obtenerPorId(id);

        assertTrue(o.isPresent());
        assertEquals(id, o.get().getId());
    }

    @Test
    void testObtenerTodos() {
        Long id = 1L;
        Orden orden = new Orden();
        orden.setId(1L);
        orden.setOrden("Empanadas");
        Pedido pedido = new Pedido(
            1L, 
            "Listo", 
            orden
        );
        
        when(repository.findAll()).thenReturn(List.of(pedido));

        List<PedidoResponseDTO> list = service.obtenerTodos();

        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getId());

    }
}
