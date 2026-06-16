package com.carrito.sbgl.Service;

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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carrito.sbgl.DTO.CarritoRequest;
import com.carrito.sbgl.DTO.CarritoResponse;
import com.carrito.sbgl.Model.Carrito;
import com.carrito.sbgl.Repository.CarritoRepository;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {
    @Mock
    private CarritoRepository repository;
    @InjectMocks
    private CarritoService service;

    @Test
    void testActualizar() {
        Long id = 2L;

        Carrito  carrio = new Carrito();
        carrio.setId(2L);
        carrio.setId_producto(1L);
        carrio.setCantidad(1);

        CarritoRequest request= new CarritoRequest(
            1L, 
            1
        );
        when(repository.findById(id)).thenReturn(Optional.of(new Carrito()));
        when(repository.save(any(Carrito.class))).thenReturn(carrio);

        Optional<CarritoResponse> optional = service.actualizar(id, request);

        assertNotNull(optional);
        assertEquals(2L, optional.get().getId());
    }

    @Test
    void testEliminar() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        service.eliminar(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void testGuardar() {

        CarritoRequest request = new CarritoRequest(
            1L, 
            2
        );
        Carrito carrito = new Carrito(
            1L, 
            1L, 
            2
        );
        when(repository.save(any(Carrito.class))).thenReturn(carrito);

        CarritoResponse response = service.guardar(request);
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void testObtenerPorId() {
        Long id=1L;
         Carrito carrito = new Carrito(
            id, 
            1L, 
            2
        );

        when(repository.findById(id)).thenReturn(Optional.of(carrito));

        Optional<CarritoResponse> optional =  service.obtenerPorId(id);
        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getId());

    }

    @Test
    void testObtenerTodos() {
        Carrito carrito = new Carrito(
            1L, 
            1L, 
            2
        );
        
        when(repository.findAll()).thenReturn(List.of(carrito));

        List<CarritoResponse> responses = service.obtenerTodos();
        assertNotNull(responses);
        assertEquals(1, responses.size());
    }
}
