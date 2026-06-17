package com.saboresglobales.carrito.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import com.saboresglobales.carrito.dto.CarritoRequest;
import com.saboresglobales.carrito.dto.CarritoResponse;
import com.saboresglobales.carrito.service.CarritoService;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarritoControllerTest {

    @Mock
    private CarritoService carritoService;

    @InjectMocks
    private CarritoController carritoController;

    private CarritoResponse carritoResponse;
    private CarritoRequest carritoRequest;
    private UUID idCarrito;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        idCarrito = UUID.randomUUID();
        clienteId = UUID.randomUUID();

        carritoResponse = new CarritoResponse();
        carritoResponse.setIdCarrito(idCarrito);
        carritoResponse.setClienteId(clienteId);
        carritoResponse.setEstado("ACTIVO");
        carritoResponse.setTotal(0.0);

        carritoRequest = new CarritoRequest();
        carritoRequest.setClienteId(clienteId);
    }

    @Test
    void cuandoCrearCarrito_debeRetornar201() {
        when(carritoService.crearCarrito(carritoRequest)).thenReturn(carritoResponse);

        ResponseEntity<CarritoResponse> respuesta = carritoController.crearCarrito(carritoRequest);

        assertEquals(201, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals("ACTIVO", respuesta.getBody().getEstado());
    }

    @Test
    void cuandoObtenerCarritoActivo_debeRetornar200() {
        when(carritoService.obtenerCarritoActivo(clienteId)).thenReturn(carritoResponse);

        ResponseEntity<CarritoResponse> respuesta = carritoController.obtenerCarritoActivo(clienteId);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void cuandoBuscarPorId_debeRetornar200() {
        when(carritoService.buscarPorId(idCarrito)).thenReturn(carritoResponse);

        ResponseEntity<CarritoResponse> respuesta = carritoController.buscarPorId(idCarrito);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals(idCarrito, respuesta.getBody().getIdCarrito());
    }

    @Test
    void cuandoConfirmarCarrito_debeRetornar200() {
        carritoResponse.setEstado("CONFIRMADO");
        when(carritoService.confirmarCarrito(idCarrito)).thenReturn(carritoResponse);

        ResponseEntity<CarritoResponse> respuesta = carritoController.confirmarCarrito(idCarrito);

        assertEquals(200, respuesta.getStatusCode().value());
        assertEquals("CONFIRMADO", respuesta.getBody().getEstado());
    }

    @Test
    void cuandoCancelarCarrito_debeRetornar200() {
        carritoResponse.setEstado("CANCELADO");
        when(carritoService.cancelarCarrito(idCarrito)).thenReturn(carritoResponse);

        ResponseEntity<CarritoResponse> respuesta = carritoController.cancelarCarrito(idCarrito);

        assertEquals(200, respuesta.getStatusCode().value());
        assertEquals("CANCELADO", respuesta.getBody().getEstado());
    }
}