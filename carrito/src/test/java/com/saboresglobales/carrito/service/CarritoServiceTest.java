package com.saboresglobales.carrito.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.saboresglobales.carrito.dto.CarritoRequest;
import com.saboresglobales.carrito.dto.CarritoResponse;
import com.saboresglobales.carrito.model.CarritoModel;
import com.saboresglobales.carrito.repository.CarritoRepository;
import com.saboresglobales.carrito.repository.ItemCarritoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ItemCarritoRepository itemCarritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    private CarritoModel carritoModel;
    private CarritoRequest carritoRequest;

    @BeforeEach
    void setUp() {
        carritoModel = new CarritoModel();
        carritoModel.setIdCarrito(UUID.randomUUID());
        carritoModel.setClienteId(UUID.randomUUID());
        carritoModel.setEstado("ACTIVO");
        carritoModel.setTotal(0.0);
        carritoModel.setFechaCreado(LocalDateTime.now());
        carritoModel.setFechaActualizado(LocalDateTime.now());

        carritoRequest = new CarritoRequest();
        carritoRequest.setClienteId(carritoModel.getClienteId());
    }

    @Test
    void cuandoCrearCarrito_debeRetornarCarritoResponse() {
        when(carritoRepository.save(any(CarritoModel.class))).thenAnswer(invocation -> {
            CarritoModel saved = invocation.getArgument(0);
            saved.setIdCarrito(UUID.randomUUID());
            return saved;
        });
        when(itemCarritoRepository.findByCarritoIdCarrito(any(UUID.class))).thenReturn(List.of());

        CarritoResponse resultado = carritoService.crearCarrito(carritoRequest);

        assertNotNull(resultado);
        assertEquals("ACTIVO", resultado.getEstado());
        assertEquals(0.0, resultado.getTotal());
    }

    @Test
    void cuandoObtenerCarritoActivo_debeRetornarCarritoResponse() {
        when(carritoRepository.findByClienteIdAndEstado(carritoModel.getClienteId(), "ACTIVO"))
                .thenReturn(carritoModel);
        when(itemCarritoRepository.findByCarritoIdCarrito(any(UUID.class))).thenReturn(List.of());

        CarritoResponse resultado = carritoService.obtenerCarritoActivo(carritoModel.getClienteId());

        assertNotNull(resultado);
        assertEquals("ACTIVO", resultado.getEstado());
    }

    @Test
    void cuandoObtenerCarritoActivoNoExiste_debeLanzarExcepcion() {
        when(carritoRepository.findByClienteIdAndEstado(any(UUID.class), eq("ACTIVO")))
                .thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> carritoService.obtenerCarritoActivo(UUID.randomUUID()));
    }

    @Test
    void cuandoBuscarPorIdExiste_debeRetornarCarritoResponse() {
        UUID id = carritoModel.getIdCarrito();
        when(carritoRepository.findById(id)).thenReturn(Optional.of(carritoModel));
        when(itemCarritoRepository.findByCarritoIdCarrito(id)).thenReturn(List.of());

        CarritoResponse resultado = carritoService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdCarrito());
    }

    @Test
    void cuandoBuscarPorIdNoExiste_debeLanzarExcepcion() {
        when(carritoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> carritoService.buscarPorId(UUID.randomUUID()));
    }

    @Test
    void cuandoConfirmarCarrito_debeRetornarEstadoConfirmado() {
        UUID id = carritoModel.getIdCarrito();

        // El carrito está ACTIVO cuando entra al método
        when(carritoRepository.findById(id)).thenReturn(Optional.of(carritoModel));
        when(carritoRepository.save(any(CarritoModel.class))).thenAnswer(invocation -> {
            CarritoModel saved = invocation.getArgument(0);
            return saved;
        });
        when(itemCarritoRepository.findByCarritoIdCarrito(id)).thenReturn(List.of());

        CarritoResponse resultado = carritoService.confirmarCarrito(id);

        assertNotNull(resultado);
        assertEquals("CONFIRMADO", resultado.getEstado());
    }

    @Test
    void cuandoCancelarCarrito_debeRetornarEstadoCancelado() {
        UUID id = carritoModel.getIdCarrito();
        when(carritoRepository.findById(id)).thenReturn(Optional.of(carritoModel));
        when(carritoRepository.save(any(CarritoModel.class))).thenReturn(carritoModel);
        when(itemCarritoRepository.findByCarritoIdCarrito(id)).thenReturn(List.of());

        carritoModel.setEstado("CANCELADO");
        CarritoResponse resultado = carritoService.cancelarCarrito(id);

        assertNotNull(resultado);
        assertEquals("CANCELADO", resultado.getEstado());
    }
}